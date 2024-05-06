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

import { getEntities } from './bevinding.reducer';

export const Bevinding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const bevindingList = useAppSelector(state => state.bevinding.entities);
  const loading = useAppSelector(state => state.bevinding.loading);

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
      <h2 id="bevinding-heading" data-cy="BevindingHeading">
        Bevindings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/bevinding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Bevinding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bevindingList && bevindingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('activiteit')}>
                  Activiteit <FontAwesomeIcon icon={getSortIconByFieldName('activiteit')} />
                </th>
                <th className="hand" onClick={sort('controleelement')}>
                  Controleelement <FontAwesomeIcon icon={getSortIconByFieldName('controleelement')} />
                </th>
                <th className="hand" onClick={sort('controleniveau')}>
                  Controleniveau <FontAwesomeIcon icon={getSortIconByFieldName('controleniveau')} />
                </th>
                <th className="hand" onClick={sort('datumaanmaak')}>
                  Datumaanmaak <FontAwesomeIcon icon={getSortIconByFieldName('datumaanmaak')} />
                </th>
                <th className="hand" onClick={sort('datummutatie')}>
                  Datummutatie <FontAwesomeIcon icon={getSortIconByFieldName('datummutatie')} />
                </th>
                <th className="hand" onClick={sort('diepte')}>
                  Diepte <FontAwesomeIcon icon={getSortIconByFieldName('diepte')} />
                </th>
                <th className="hand" onClick={sort('fase')}>
                  Fase <FontAwesomeIcon icon={getSortIconByFieldName('fase')} />
                </th>
                <th className="hand" onClick={sort('gemuteerddoor')}>
                  Gemuteerddoor <FontAwesomeIcon icon={getSortIconByFieldName('gemuteerddoor')} />
                </th>
                <th className="hand" onClick={sort('resultaat')}>
                  Resultaat <FontAwesomeIcon icon={getSortIconByFieldName('resultaat')} />
                </th>
                <th className="hand" onClick={sort('risico')}>
                  Risico <FontAwesomeIcon icon={getSortIconByFieldName('risico')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bevindingList.map((bevinding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bevinding/${bevinding.id}`} color="link" size="sm">
                      {bevinding.id}
                    </Button>
                  </td>
                  <td>{bevinding.aangemaaktdoor}</td>
                  <td>{bevinding.activiteit}</td>
                  <td>{bevinding.controleelement}</td>
                  <td>{bevinding.controleniveau}</td>
                  <td>
                    {bevinding.datumaanmaak ? (
                      <TextFormat type="date" value={bevinding.datumaanmaak} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {bevinding.datummutatie ? (
                      <TextFormat type="date" value={bevinding.datummutatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{bevinding.diepte}</td>
                  <td>{bevinding.fase}</td>
                  <td>{bevinding.gemuteerddoor}</td>
                  <td>{bevinding.resultaat}</td>
                  <td>{bevinding.risico}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bevinding/${bevinding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/bevinding/${bevinding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/bevinding/${bevinding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Bevindings found</div>
        )}
      </div>
    </div>
  );
};

export default Bevinding;
