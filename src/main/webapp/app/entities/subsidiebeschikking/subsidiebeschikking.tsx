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

import { getEntities } from './subsidiebeschikking.reducer';

export const Subsidiebeschikking = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const subsidiebeschikkingList = useAppSelector(state => state.subsidiebeschikking.entities);
  const loading = useAppSelector(state => state.subsidiebeschikking.loading);

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
      <h2 id="subsidiebeschikking-heading" data-cy="SubsidiebeschikkingHeading">
        Subsidiebeschikkings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/subsidiebeschikking/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Subsidiebeschikking
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {subsidiebeschikkingList && subsidiebeschikkingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beschikkingsnummer')}>
                  Beschikkingsnummer <FontAwesomeIcon icon={getSortIconByFieldName('beschikkingsnummer')} />
                </th>
                <th className="hand" onClick={sort('beschiktbedrag')}>
                  Beschiktbedrag <FontAwesomeIcon icon={getSortIconByFieldName('beschiktbedrag')} />
                </th>
                <th className="hand" onClick={sort('besluit')}>
                  Besluit <FontAwesomeIcon icon={getSortIconByFieldName('besluit')} />
                </th>
                <th className="hand" onClick={sort('internkenmerk')}>
                  Internkenmerk <FontAwesomeIcon icon={getSortIconByFieldName('internkenmerk')} />
                </th>
                <th className="hand" onClick={sort('kenmerk')}>
                  Kenmerk <FontAwesomeIcon icon={getSortIconByFieldName('kenmerk')} />
                </th>
                <th className="hand" onClick={sort('ontvangen')}>
                  Ontvangen <FontAwesomeIcon icon={getSortIconByFieldName('ontvangen')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th>
                  Betreft Subsidie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subsidiebeschikkingList.map((subsidiebeschikking, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/subsidiebeschikking/${subsidiebeschikking.id}`} color="link" size="sm">
                      {subsidiebeschikking.id}
                    </Button>
                  </td>
                  <td>{subsidiebeschikking.beschikkingsnummer}</td>
                  <td>{subsidiebeschikking.beschiktbedrag}</td>
                  <td>{subsidiebeschikking.besluit}</td>
                  <td>{subsidiebeschikking.internkenmerk}</td>
                  <td>{subsidiebeschikking.kenmerk}</td>
                  <td>
                    {subsidiebeschikking.ontvangen ? (
                      <TextFormat type="date" value={subsidiebeschikking.ontvangen} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{subsidiebeschikking.opmerkingen}</td>
                  <td>
                    {subsidiebeschikking.betreftSubsidie ? (
                      <Link to={`/subsidie/${subsidiebeschikking.betreftSubsidie.id}`}>{subsidiebeschikking.betreftSubsidie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/subsidiebeschikking/${subsidiebeschikking.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/subsidiebeschikking/${subsidiebeschikking.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/subsidiebeschikking/${subsidiebeschikking.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Subsidiebeschikkings found</div>
        )}
      </div>
    </div>
  );
};

export default Subsidiebeschikking;
