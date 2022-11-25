package jp.matsuura.household_accountandroid.domain

import jp.matsuura.household_accountandroid.data.repository.CategoryRepository
import jp.matsuura.household_accountandroid.model.CategoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
){

    suspend operator fun invoke(): List<CategoryModel> {
        return categoryRepository.getAll()
    }
}