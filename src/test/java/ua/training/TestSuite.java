package ua.training;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.controller.command.HomeCommandTest;
import ua.training.controller.command.PageNotFoundCommandTest;
import ua.training.controller.command.auth.GetLoginCommandTest;
import ua.training.controller.command.auth.LogoutCommandTest;
import ua.training.controller.command.auth.PostLoginCommandTest;
import ua.training.controller.command.category.*;
import ua.training.controller.command.dish.*;
import ua.training.controller.command.i18n.ChangeLocaleCommandTest;
import ua.training.controller.command.order.*;
import ua.training.controller.command.user.*;
import ua.training.controller.utils.CommandKeyGeneratorTest;
import ua.training.controller.utils.SessionManagerTest;
import ua.training.service.CategoryServiceTest;
import ua.training.service.DishServiceTest;
import ua.training.service.OrderServiceTest;
import ua.training.service.UserServiceTest;
import ua.training.validator.entity.CategoryValidatorTest;
import ua.training.validator.entity.CredentialsDtoValidatorTest;
import ua.training.validator.entity.DishDtoValidatorTest;
import ua.training.validator.entity.UserDtoValidatorTest;
import ua.training.validator.field.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({CategoryServiceTest.class, DishServiceTest.class, OrderServiceTest.class, UserServiceTest.class,
        CategoryValidatorTest.class, CredentialsDtoValidatorTest.class, DishDtoValidatorTest.class, UserDtoValidatorTest.class,
        HomeCommandTest.class, PageNotFoundCommandTest.class,
        AllUsersCommandTest.class, DeleteUserCommandTest.class,
        GetAddUserCommandTest.class, GetUpdateUserCommandTest.class,
        PostAddUserCommandTest.class, PostUpdateUserCommandTest.class,
        SearchBestWaitersPerPeriodTest.class, SearchUsersByRoleCommandTest.class,
        SearchUsersBySurnameCommandTest.class,
        AllOrdersCommandTest.class, DeleteOrderCommandTest.class,
        GetAddOrderCommandTest.class, GetUpdateOrderCommandTest.class,
        PostAddOrderCommandTest.class, PostUpdateOrderCommandTest.class,
        SearchOrdersPerPeriodTest.class,
        ChangeLocaleCommandTest.class,
        GetLoginCommandTest.class, LogoutCommandTest.class, PostLoginCommandTest.class,
        AllDishesCommandTest.class, DeleteDishCommandTest.class, GetAddDishCommandTest.class,
        GetUpdateDishCommandTest.class, PostAddDishCommandTest.class,
        PostUpdateDishCommandTest.class, SearchDishesByCategoryCommandTest.class,
        SearchDishesByNameCommandTest.class, SearchMostPopularDishesPerPeriodCommandTest.class,
        AllCategoriesCommandTest.class, DeleteCategoryCommandTest.class,
        GetAddCategoryCommandTest.class, GetUpdateCategoryCommandTest.class,
        PostAddCategoryCommandTest.class, PostUpdateCategoryCommandTest.class, SearchCategoriesByNameTest.class,
        AddressValidatorTest.class, CostValidatorTest.class, DescriptionValidatorTest.class,
        EmailValidatorTest.class, NameValidatorTest.class, PasswordValidatorTest.class,
        PhoneValidatorTest.class, SurnameValidatorTest.class, WeightValidatorTest.class,
        CommandKeyGeneratorTest.class, SessionManagerTest.class})
public class TestSuite {
    /*
     * empty class
     */
}
