package exceptions

import models.HubCommand

class UnknownHubCommand(command: HubCommand) : Throwable("Wrong command $command at mapping toTransport stage")