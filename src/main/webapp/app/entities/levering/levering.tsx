import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './levering.reducer';

export const Levering = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const leveringList = useAppSelector(state => state.levering.entities);
  const loading = useAppSelector(state => state.levering.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="levering-heading" data-cy="LeveringHeading">
        Leverings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/levering/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Levering
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leveringList && leveringList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumstop')}>
                  Datumstop <FontAwesomeIcon icon={getSortIconByFieldName('datumstop')} />
                </th>
                <th className="hand" onClick={sort('eenheid')}>
                  Eenheid <FontAwesomeIcon icon={getSortIconByFieldName('eenheid')} />
                </th>
                <th className="hand" onClick={sort('frequentie')}>
                  Frequentie <FontAwesomeIcon icon={getSortIconByFieldName('frequentie')} />
                </th>
                <th className="hand" onClick={sort('omvang')}>
                  Omvang <FontAwesomeIcon icon={getSortIconByFieldName('omvang')} />
                </th>
                <th className="hand" onClick={sort('stopreden')}>
                  Stopreden <FontAwesomeIcon icon={getSortIconByFieldName('stopreden')} />
                </th>
                <th>
                  Geleverdeprestatie Beschikking <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Prestatievoor Client <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Geleverdezorg Toewijzing <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voorziening Voorziening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leverdeprestatie Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leveringList.map((levering, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/levering/${levering.id}`} color="link" size="sm">
                      {levering.id}
                    </Button>
                  </td>
                  <td>{levering.code}</td>
                  <td>
                    {levering.datumstart ? <TextFormat type="date" value={levering.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {levering.datumstop ? <TextFormat type="date" value={levering.datumstop} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{levering.eenheid}</td>
                  <td>{levering.frequentie}</td>
                  <td>{levering.omvang}</td>
                  <td>{levering.stopreden}</td>
                  <td>
                    {levering.geleverdeprestatieBeschikking ? (
                      <Link to={`/beschikking/${levering.geleverdeprestatieBeschikking.id}`}>
                        {levering.geleverdeprestatieBeschikking.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {levering.prestatievoorClient ? (
                      <Link to={`/client/${levering.prestatievoorClient.id}`}>{levering.prestatievoorClient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {levering.geleverdezorgToewijzing ? (
                      <Link to={`/toewijzing/${levering.geleverdezorgToewijzing.id}`}>{levering.geleverdezorgToewijzing.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {levering.voorzieningVoorziening ? (
                      <Link to={`/voorziening/${levering.voorzieningVoorziening.id}`}>{levering.voorzieningVoorziening.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {levering.leverdeprestatieLeverancier ? (
                      <Link to={`/leverancier/${levering.leverdeprestatieLeverancier.id}`}>{levering.leverdeprestatieLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/levering/${levering.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/levering/${levering.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/levering/${levering.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Leverings found</div>
        )}
      </div>
    </div>
  );
};

export default Levering;
