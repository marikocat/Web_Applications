package com.my.command;

import com.my.command.account.RefillUserAccountCommand;
import com.my.command.account.ShowUserAccountCommand;
import com.my.command.cart.AddToCartCommand;
import com.my.command.cart.RemoveFromCartCommand;
import com.my.command.cart.ShowCartCommand;
import com.my.command.plan.*;
import com.my.command.registration.*;
import com.my.command.user.CreateUserCommand;
import com.my.command.user.DeleteUserCommand;
import com.my.command.user.UpdateUserCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("showLoginForm", new ShowLoginFormCommand()); //get
        commands.put("logIn", new LogInCommand()); //post
        commands.put("logOut", new LogOutCommand()); //get
        commands.put("showAdminPage", new ShowAdminPageCommand()); //get
        commands.put("showSubscriberPage", new ShowSubscriberPageCommand()); //get

        commands.put("createUser", new CreateUserCommand()); //post
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("deleteUser", new DeleteUserCommand());

        commands.put("fillRegisterForm", new FillRegisterFormCommand()); //get
        commands.put("showRegisterForm", new ShowRegisterFormCommand()); //get
        commands.put("showRequestForm", new ShowRequestFormCommand()); //get
        commands.put("showRequestAnswer", new ShowRequestAnswerCommand()); //get
        commands.put("createRequest", new CreateRequestCommand()); //post
        commands.put("deleteRequest", new DeleteRequestCommand()); //post

        commands.put("listUsers", new ListUsersCommand()); //get
        commands.put("listRequests", new ListRequestsCommand()); //get
        commands.put("listServices", new ListServicesCommand()); //get
        commands.put("listTariffsByService", new ListTariffsByServiceCommand()); //get
        commands.put("listTariffsByPage", new ListTariffsByPageCommand()); //get
        commands.put("editTariff", new EditTariffCommand()); //get

        commands.put("downloadFile", new DownloadFileCommand());

        commands.put("addToCart", new AddToCartCommand()); //post
        commands.put("removeFromCart", new RemoveFromCartCommand()); //post
        commands.put("showCart", new ShowCartCommand()); //get

        commands.put("createTariffPlan", new CreateTariffPlanCommand()); //post
        commands.put("deleteTariffPlan", new DeleteTariffPlanCommand()); //post
        commands.put("showTariffPlan", new ShowTariffPlanCommand()); //get
        commands.put("changeTariffPlanStatus", new ChangeTariffPlanStatusCommand()); //post

        commands.put("showUserAccount", new ShowUserAccountCommand()); //get
        commands.put("refillUserAccount", new RefillUserAccountCommand()); //post
    }

    private CommandContainer() {

    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
