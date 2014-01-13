package de.htwg.seapal.web.controllers;

import com.google.inject.Inject;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.*;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

public final class MainAPI
        extends Controller {

    private static final JsonNode success = Json.parse("{\"success\":true}");
    private static final JsonNode fail = Json.parse("{\"success\":false}");
    private static final JsonNode EMPTY = Json.parse("{\"error\":\"no such document\"}");

    @Inject
    private IMainController controller;

    private Map<String, Form<? extends ModelDocument>> forms;

    public MainAPI() {
        forms = new HashMap<>();
        forms.put("boat", Form.form(Boat.class));
        forms.put("mark", Form.form(Mark.class));
        forms.put("route", Form.form(Route.class));
        forms.put("trip", Form.form(Trip.class));
        forms.put("waypoint", Form.form(Waypoint.class));
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result singleDocument(final UUID id, final String document) {

        return ok(Json.toJson(controller.getSingleDocument(session(IPersonController.AUTHN_COOKIE_KEY), id, document)));
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result deleteDocument(final UUID id, final String document) {
        if (controller.deleteDocument(session(IPersonController.AUTHN_COOKIE_KEY), id, document)) {
            return ok(success);
        } else {
            return unauthorized(fail);
        }
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result getDocuments(String document, String scope) {
        Collection<IModel> list = new LinkedList<>();
        String session = session(IPersonController.AUTHN_COOKIE_KEY);

        if (scope.equals("all") || scope.equals("own")) {
            list.addAll(controller.getOwnDocuments(document, session));
        }

        if (scope.equals("all") || scope.equals("foreign")) {
            list.addAll(controller.getForeignDocuments(document, session));
        }

        return ok(Json.toJson(list));
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result getByParent(String document, String parent, UUID id) {
        try {
            return ok(Json.toJson(controller.getByParent(document, parent, session(IPersonController.AUTHN_COOKIE_KEY), id)));
        } catch (NullPointerException e) {
            return internalServerError(EMPTY);
        }
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result createDocument(String document) {
        try {
            ModelDocument doc = forms.get(document).bindFromRequest().get();
            doc.setAccount(session(IPersonController.AUTHN_COOKIE_KEY));
            return ok(Json.toJson(controller.creatDocument(document, doc)));
        } catch (NullPointerException e) {
            return internalServerError(EMPTY);
        }
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result ownAccount() {
        try {
            return ok(Json.toJson(controller.account(session(IPersonController.AUTHN_COOKIE_KEY))));
        } catch (NullPointerException e) {
            return internalServerError(EMPTY);
        }
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result account(UUID id) {
        try {
            return ok(Json.toJson(controller.account(id, session(IPersonController.AUTHN_COOKIE_KEY))));
        } catch (NullPointerException e) {
            return internalServerError(EMPTY);
        }
    }

    @play.mvc.Security.Authenticated(AccountAPI.SecuredAPI.class)
    public Result sendFriendRequest(UUID askedPersonUUID) {
        try {
            return ok(Json.toJson(controller.addFriend(session(IPersonController.AUTHN_COOKIE_KEY), askedPersonUUID)));
        } catch (NullPointerException e) {
            return internalServerError(EMPTY);
        }
    }
}