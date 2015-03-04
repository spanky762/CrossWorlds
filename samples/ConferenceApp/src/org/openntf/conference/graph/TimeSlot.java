package org.openntf.conference.graph;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openntf.conference.graph.Event.HappeningOn;
import org.openntf.domino.graph2.annotations.AdjacencyUnique;
import org.openntf.domino.graph2.annotations.IncidenceUnique;
import org.openntf.domino.graph2.annotations.TypedProperty;
import org.openntf.domino.graph2.builtin.DVertexFrame;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.modules.javahandler.JavaHandler;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerClass;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@JavaHandlerClass(TimeSlot.TimeSlotImpl.class)
@TypeValue("TimeSlot")
public interface TimeSlot extends DVertexFrame {
	public abstract static class TimeSlotImpl implements TimeSlot {
		@Override
		public Integer getDuration() {
			Integer result = Integer.valueOf(0);
			try {
				Calendar start = getStartTime();
				Calendar end = getEndTime();
				long msDifference = end.getTime().getTime() - start.getTime().getTime();
				result = Integer.valueOf(Long.valueOf(msDifference / 60000).intValue());
			} catch (Throwable t) {
				//TODO what?
			}
			return result;
		}
		
		@Override
		public String getDay() {
			String result = "";
			try {
				Calendar start = getStartTime();
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM");
				result = DATE_FORMAT.format(start.getTime());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return result;
		}
	}

	@TypedProperty("Starttime")
	public Calendar getStartTime();

	@TypedProperty("Starttime")
	public void setStartTime(Calendar startTime);

	@TypedProperty("Endtime")
	public Calendar getEndTime();

	@TypedProperty("Endtime")
	public void setEndTime(Calendar endTime);

	@JavaHandler
	public Integer getDuration();	//in minutes
	
	@JavaHandler
	public String getDay(); // in dd MMM format

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public Iterable<Event> getEvents();

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public HappeningOn addEvent(Event event);

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public void removingEvent(Event event);

	@IncidenceUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public Iterable<HappeningOn> getHappeningOns();

	@IncidenceUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public void removingHappeningOn(HappeningOn happeningOn);

}
