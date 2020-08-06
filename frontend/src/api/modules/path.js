import ApiService from '@/api'

const BASE_URL = `/paths`

const PathService = {
    get(queryParam) {
        return ApiService.get(`${BASE_URL}?` + queryParam)
    }
}

export default PathService
