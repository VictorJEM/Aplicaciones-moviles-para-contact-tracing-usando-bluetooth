package com.espol.asi_simulation.Backend_ASI_Simulation.Services;

//import java.time.Duration;
import java.util.List;
import com.espol.asi_simulation.Backend_ASI_Simulation.Model.Exposee;

public interface DPPPTDataService {
	
	/**
	   * Upserts (Update or Inserts) the given exposee
	   *
	   * @param exposee the exposee to upsert
	   * @param appSource the app name
	   */
	  void upsertExposee(Exposee exposee);

	  /**
	   * Upserts (Update or Inserts) the given exposees (if keys cannot be derived from one master key)
	   *
	   * @param exposees the list of exposees to upsert
	   * @param appSource the app name
	   */
	  //void upsertExposees(List<Exposee> exposees, String appSource);

	  /**
	   * Returns the maximum id of the stored exposed entries fo the given batch.
	   *
	   * @param batchReleaseTime in milliseconds since the start of the Unix Epoch, must be a multiple
	   *     of
	   * @param releaseBucketDuration in milliseconds
	   * @return the maximum id of the stored exposed entries fo the given batch
	   */
	  //int getMaxExposedIdForBatchReleaseTime(long batchReleaseTime, long releaseBucketDuration);

	  /**
	   * Returns all exposees for the given batch.
	   *
	   * @param batchReleaseTime in milliseconds since the start of the Unix Epoch, must be a multiple
	   *     of
	   * @param releaseBucketDuration in milliseconds
	   * @return all exposees for the given batch
	   */
	  //List<Exposee> getSortedExposedForBatchReleaseTime(long batchReleaseTime, long releaseBucketDuration);
	  List<Exposee> getListExposedForBatchReleaseTime(long batchReleaseTime);

	  /**
	   * deletes entries older than retentionperiod
	   *
	   * @param retentionPeriod duration of retention period for exposed keys
	   */
	  //void cleanDB(Duration retentionPeriod);

}
