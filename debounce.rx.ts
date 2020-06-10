import { Observable } from 'rxjs';

const notEmpty = (input: string): boolean => !!input && input.trim().length > 0;

const sendRequest = (arr: Array<any>, query: string): any => {
    return arr.filter( (item) => {
        return query.length > 0 && item.startsWith(query);
    });
};

const search$ = Observable.fromEvent(searchBox, 'keyup')
                            .debounceTime(1000)
                            .pluck('target', 'value')
                            .filter(notEmpty)
                            .do( (query: string): void  => console.log(`Querying for ${query} ....`))
                            .map( (query: string): any => sendRequest(testData, query) )
                            .subscribe( (result: string): void => {
                                clearResults(result);
                                appendResult(result);
                            }); 