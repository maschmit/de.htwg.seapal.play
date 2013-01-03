package models;

import java.util.Date;
import java.util.List;

import play.data.validation.Constraints;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Trip extends Model {

	private static final long serialVersionUID = 2L;

	@Id
	@Constraints.Min(0)
	private Long id;
	
	@Constraints.Required
	public Long boat_id;
	
	@Constraints.Required
	public String trip_title;
	
	public String trip_from;
	
	public String trip_to;
	
	public String crew;
	
	@Formats.DateTime(pattern="yyyy-mm-dd")
	public Date start_time;
	
	@Formats.DateTime(pattern="yyyy-mm-dd")
	public Date end_time;
	
	public Integer engine_runtime;
	
	public String skipper;
	
	public Boolean tank_filled;
	
	public String note;
	
	/* end of fields */
    
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		if(id > 0){
			this.id = id;
		}
	}

	/* end of getter/setter */

	public static Finder<Long, Trip> find = new Finder<Long, Trip>(Long.class, Trip.class);

	public static List<Trip> all() {
		return find.all();
	}
	
	public static void update(Trip trip){
		trip.update(trip.getId());
	}

	public static void create(Trip trip) {
		trip.save();
	}
	
	public static Trip findById(Long id) {
		return Trip.find.ref(id);
	}

	public static void delete(Long id) {
		Trip.find.ref(id).delete();
	}

}
