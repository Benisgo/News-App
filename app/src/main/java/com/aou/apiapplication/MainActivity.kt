import android.os.Bundle
import android.widget.Adapter
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.aou.apiapplication.Constants
import com.aou.apiapplication.NewsAdapter
import com.aou.apiapplication.R
import com.aou.apiapplication.api.ApiManager
import com.aou.apiapplication.model.ArticlesItem
import com.aou.apiapplication.model.NewsResponse
import com.aou.apiapplication.model.SourcesItem
import com.aou.apiapplication.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    lateinit var recycler_view : RecyclerView

    lateinit var tab_layout : TabLayout
    lateinit var progressBar : ProgressBar

    lateinit var adapter : NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()



        ApiManager.getApi().getSources(Constants.ApiKey)
            .enqueue(object : Callback<SourcesResponse> {

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible = false

                    addSourcesToTabLayout(response.body()?.sources)
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun initViews(){

        recycler_view = findViewById(R.id.recycler_view)
        tab_layout = findViewById(R.id.tab_layout)
        progressBar = findViewById(R.id.Progress_Bar)

        var adapter = NewsAdapter(null)

        recycler_view.adapter = adapter
    }

    fun addSourcesToTabLayout(sources:List<SourcesItem?>?) {

        sources?.forEach{ source ->

            val tab = tab_layout.newTab()
            tab.setText(source?.name)

            tab.tag = source

            tab_layout.addTab(tab)
        }

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab = bbc-news
//                tab.tag = source

                val source = tab?.tag as SourcesItem

                getNewsBySource(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })

    }

    fun getNewsBySource(source:SourcesItem){

        progressBar.isVisible = true

        ApiManager.getApi().getNews(Constants.ApiKey,source.id?:"")
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBar.isVisible = false

                    adapter.changedata(response.body()?.articles as List<ArticlesItem>?)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    // do whatever
                }
            })

    }
}
