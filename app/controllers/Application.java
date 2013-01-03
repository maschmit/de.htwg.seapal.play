package controllers;

import controllers.helpers.Menus;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.content.*;

@With(Menus.class)
public class Application extends Controller {
	
	public static Result index() {
		return ok(index.render());
	}

	public static Result screenshots() {
		return ok(screenshots.render());
	}

	public static Result about() {
		return ok(about.render());
	}

	public static Result contact() {
		return ok(contact.render());
	}

	public static Result user_guide() {
		return ok(user_guide.render());
	}
	
	public static Result seamap(){
		return ok(seamap.render());
	}
	
	public static Result boat_info() {
		return ok(boat_info.render());
	}
	
	public static Result trip_list(Long boatId) {
		return ok(trip_list.render(boatId));
	}
	
	public static Result trip_add(Long boatId) {
		return ok(trip_info.render(boatId, new Long(-1)));
	}
	
	public static Result trip_edit(Long tripId) {
		return ok(trip_info.render(new Long(-1), tripId));
	}
	
	public static Result waypoint_add(Long tripId){
		return ok(log_entry.render(tripId));
	}
	
	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	      Routes.javascriptRouter("jsRoutes",
	        // Routes
	    	// Application
    		controllers.routes.javascript.Application.trip_list(),
  	        controllers.routes.javascript.Application.trip_edit(),
	    	// API  
	        controllers.routes.javascript.BoatAPI.boatAsJson(),
	        controllers.routes.javascript.BoatAPI.boatsAsJson(),
	        controllers.routes.javascript.BoatAPI.deleteBoat(),
	        controllers.routes.javascript.BoatAPI.addBoat(),
	        controllers.routes.javascript.TripAPI.tripsAsJson(),
	        controllers.routes.javascript.TripAPI.tripAsJson(),
	        controllers.routes.javascript.TripAPI.alltripsAsJson(),
	        controllers.routes.javascript.TripAPI.addTrip(),
	        controllers.routes.javascript.WaypointAPI.addWaypoint(),
	        controllers.routes.javascript.WaypointAPI.waypointsAsJson(),
	        controllers.routes.javascript.BoatPositionAPI.current()
	      )
	    );
	  }
}