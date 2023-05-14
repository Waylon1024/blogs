import axios from '@/plugins/axios'

export function getSentence() {
	return axios({
		url: 'getSentence',
		method: 'GET'
	})
}

export function getSite() {
	return axios({
		url: 'site',
		method: 'GET'
	})
}
