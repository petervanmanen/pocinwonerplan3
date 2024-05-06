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

import { getEntities } from './attribuutsoort.reducer';

export const Attribuutsoort = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const attribuutsoortList = useAppSelector(state => state.attribuutsoort.entities);
  const loading = useAppSelector(state => state.attribuutsoort.loading);

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
      <h2 id="attribuutsoort-heading" data-cy="AttribuutsoortHeading">
        Attribuutsoorts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/attribuutsoort/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Attribuutsoort
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attribuutsoortList && attribuutsoortList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('authentiek')}>
                  Authentiek <FontAwesomeIcon icon={getSortIconByFieldName('authentiek')} />
                </th>
                <th className="hand" onClick={sort('datumopname')}>
                  Datumopname <FontAwesomeIcon icon={getSortIconByFieldName('datumopname')} />
                </th>
                <th className="hand" onClick={sort('definitie')}>
                  Definitie <FontAwesomeIcon icon={getSortIconByFieldName('definitie')} />
                </th>
                <th className="hand" onClick={sort('domein')}>
                  Domein <FontAwesomeIcon icon={getSortIconByFieldName('domein')} />
                </th>
                <th className="hand" onClick={sort('eaguid')}>
                  Eaguid <FontAwesomeIcon icon={getSortIconByFieldName('eaguid')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('herkomstdefinitie')}>
                  Herkomstdefinitie <FontAwesomeIcon icon={getSortIconByFieldName('herkomstdefinitie')} />
                </th>
                <th className="hand" onClick={sort('id')}>
                  Id <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('identificerend')}>
                  Identificerend <FontAwesomeIcon icon={getSortIconByFieldName('identificerend')} />
                </th>
                <th className="hand" onClick={sort('indicatieafleidbaar')}>
                  Indicatieafleidbaar <FontAwesomeIcon icon={getSortIconByFieldName('indicatieafleidbaar')} />
                </th>
                <th className="hand" onClick={sort('indicatiematerielehistorie')}>
                  Indicatiematerielehistorie <FontAwesomeIcon icon={getSortIconByFieldName('indicatiematerielehistorie')} />
                </th>
                <th className="hand" onClick={sort('kardinaliteit')}>
                  Kardinaliteit <FontAwesomeIcon icon={getSortIconByFieldName('kardinaliteit')} />
                </th>
                <th className="hand" onClick={sort('lengte')}>
                  Lengte <FontAwesomeIcon icon={getSortIconByFieldName('lengte')} />
                </th>
                <th className="hand" onClick={sort('mogelijkgeenwaarde')}>
                  Mogelijkgeenwaarde <FontAwesomeIcon icon={getSortIconByFieldName('mogelijkgeenwaarde')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('patroon')}>
                  Patroon <FontAwesomeIcon icon={getSortIconByFieldName('patroon')} />
                </th>
                <th className="hand" onClick={sort('precisie')}>
                  Precisie <FontAwesomeIcon icon={getSortIconByFieldName('precisie')} />
                </th>
                <th className="hand" onClick={sort('stereotype')}>
                  Stereotype <FontAwesomeIcon icon={getSortIconByFieldName('stereotype')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th>
                  Heeft Datatype <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {attribuutsoortList.map((attribuutsoort, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/attribuutsoort/${attribuutsoort.id}`} color="link" size="sm">
                      {attribuutsoort.id}
                    </Button>
                  </td>
                  <td>{attribuutsoort.authentiek ? 'true' : 'false'}</td>
                  <td>
                    {attribuutsoort.datumopname ? (
                      <TextFormat type="date" value={attribuutsoort.datumopname} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{attribuutsoort.definitie}</td>
                  <td>{attribuutsoort.domein}</td>
                  <td>{attribuutsoort.eaguid}</td>
                  <td>{attribuutsoort.herkomst}</td>
                  <td>{attribuutsoort.herkomstdefinitie}</td>
                  <td>{attribuutsoort.identificerend ? 'true' : 'false'}</td>
                  <td>{attribuutsoort.indicatieafleidbaar ? 'true' : 'false'}</td>
                  <td>{attribuutsoort.indicatiematerielehistorie ? 'true' : 'false'}</td>
                  <td>{attribuutsoort.kardinaliteit}</td>
                  <td>{attribuutsoort.lengte}</td>
                  <td>{attribuutsoort.mogelijkgeenwaarde ? 'true' : 'false'}</td>
                  <td>{attribuutsoort.naam}</td>
                  <td>{attribuutsoort.patroon}</td>
                  <td>{attribuutsoort.precisie}</td>
                  <td>{attribuutsoort.stereotype}</td>
                  <td>{attribuutsoort.toelichting}</td>
                  <td>
                    {attribuutsoort.heeftDatatype ? (
                      <Link to={`/datatype/${attribuutsoort.heeftDatatype.id}`}>{attribuutsoort.heeftDatatype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/attribuutsoort/${attribuutsoort.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/attribuutsoort/${attribuutsoort.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/attribuutsoort/${attribuutsoort.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Attribuutsoorts found</div>
        )}
      </div>
    </div>
  );
};

export default Attribuutsoort;
