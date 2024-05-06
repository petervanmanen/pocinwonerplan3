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

import { getEntities } from './bruikleen.reducer';

export const Bruikleen = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const bruikleenList = useAppSelector(state => state.bruikleen.entities);
  const loading = useAppSelector(state => state.bruikleen.loading);

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
      <h2 id="bruikleen-heading" data-cy="BruikleenHeading">
        Bruikleens
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/bruikleen/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Bruikleen
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bruikleenList && bruikleenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanvraagdoor')}>
                  Aanvraagdoor <FontAwesomeIcon icon={getSortIconByFieldName('aanvraagdoor')} />
                </th>
                <th className="hand" onClick={sort('datumaanvraag')}>
                  Datumaanvraag <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvraag')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('toestemmingdoor')}>
                  Toestemmingdoor <FontAwesomeIcon icon={getSortIconByFieldName('toestemmingdoor')} />
                </th>
                <th>
                  Isbedoeldvoor Tentoonstelling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Is Lener <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bruikleenList.map((bruikleen, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bruikleen/${bruikleen.id}`} color="link" size="sm">
                      {bruikleen.id}
                    </Button>
                  </td>
                  <td>{bruikleen.aanvraagdoor}</td>
                  <td>
                    {bruikleen.datumaanvraag ? (
                      <TextFormat type="date" value={bruikleen.datumaanvraag} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bruikleen.datumeinde ? <TextFormat type="date" value={bruikleen.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {bruikleen.datumstart ? <TextFormat type="date" value={bruikleen.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{bruikleen.toestemmingdoor}</td>
                  <td>
                    {bruikleen.isbedoeldvoorTentoonstellings
                      ? bruikleen.isbedoeldvoorTentoonstellings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/tentoonstelling/${val.id}`}>{val.id}</Link>
                            {j === bruikleen.isbedoeldvoorTentoonstellings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {bruikleen.isLeners
                      ? bruikleen.isLeners.map((val, j) => (
                          <span key={j}>
                            <Link to={`/lener/${val.id}`}>{val.id}</Link>
                            {j === bruikleen.isLeners.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bruikleen/${bruikleen.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/bruikleen/${bruikleen.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/bruikleen/${bruikleen.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Bruikleens found</div>
        )}
      </div>
    </div>
  );
};

export default Bruikleen;
