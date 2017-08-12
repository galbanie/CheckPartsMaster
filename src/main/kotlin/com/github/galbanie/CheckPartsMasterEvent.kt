package com.github.galbanie

import com.github.galbanie.models.CheckParts
import com.github.galbanie.models.Part
import com.github.galbanie.models.Result
import com.github.galbanie.models.Source
import com.github.galbanie.utils.AddType
import tornadofx.*
import java.io.File
import java.util.*

/**
 * Created by Galbanie on 2017-08-01.
 */

// Events Sources
object SourceListRequest : FXEvent(EventBus.RunOn.BackgroundThread)
class SourceCreated(val source : Source) : FXEvent()
class SourceUpdated(val source : Source) : FXEvent()
class SourceRemoved(val source : Source) : FXEvent()
class SourceListFound(val sources : List<Source>) : FXEvent()
class SourceSelectedListFound(val sourcesSelected : List<Source>) : FXEvent()
class LoadSourceDataFromFile(val file : File) : FXEvent()
class SaveSourceDataToFile(val file : File) : FXEvent()

// Events Check Parts
object CheckPartsListRequest : FXEvent(EventBus.RunOn.BackgroundThread)
class CheckPartsListFound(val checks : List<CheckParts>) : FXEvent()
class CheckPartsQuery(val id : UUID) : FXEvent(EventBus.RunOn.BackgroundThread)
class CheckPartsCreated(val check : CheckParts) : FXEvent()

// Events Parts
class PartsListFound(val checkId : UUID, val parts: List<Part>) : FXEvent()
class PartsAdded(val checkId : UUID, val parts: String, val mode : AddType = AddType.add) : FXEvent()
class PartsRemoved(val checkId : UUID, val parts: List<Part>) : FXEvent()
class PartUpdated(val checkId : UUID, val oldPart : Part, val newPart : Part) : FXEvent()
class PartsReinitialized(val checkId : UUID, val parts: List<Part>) : FXEvent()

// Events Results
class ResultsListFound(val results : List<Result>) : FXEvent()

// Others Events
object Run : FXEvent(EventBus.RunOn.BackgroundThread)
object Stop : FXEvent(EventBus.RunOn.BackgroundThread)
object Clear : FXEvent(EventBus.RunOn.BackgroundThread)

