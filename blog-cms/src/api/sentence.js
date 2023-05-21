import axios from '@/util/request'

export function getData(queryInfo) {
    return axios({
        url: 'sentences',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function getType() {
    return axios({
        url: 'sentencesTypeList',
        method: 'GET',
    })
}


export function editSentence(form) {
    return axios({
        url: 'editSentence',
        method: 'PUT',
        data: {
            ...form
        }
    })
}

export function addSingleSentence(form){
    return axios({
        url: 'addSingleSentence',
        method: 'POST',
        data:{
            ...form
        }
    })
}
export function addExcelSentence(sentenceList) {
    return axios({
        url: 'addExcelSentence',
        method: 'POST',
        data: sentenceList
    });
}

