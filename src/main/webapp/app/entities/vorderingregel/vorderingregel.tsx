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

import { getEntities } from './vorderingregel.reducer';

export const Vorderingregel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vorderingregelList = useAppSelector(state => state.vorderingregel.entities);
  const loading = useAppSelector(state => state.vorderingregel.loading);

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
      <h2 id="vorderingregel-heading" data-cy="VorderingregelHeading">
        Vorderingregels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vorderingregel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vorderingregel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vorderingregelList && vorderingregelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('aanmaakdatum')}>
                  Aanmaakdatum <FontAwesomeIcon icon={getSortIconByFieldName('aanmaakdatum')} />
                </th>
                <th className="hand" onClick={sort('bedragexclbtw')}>
                  Bedragexclbtw <FontAwesomeIcon icon={getSortIconByFieldName('bedragexclbtw')} />
                </th>
                <th className="hand" onClick={sort('bedraginclbtw')}>
                  Bedraginclbtw <FontAwesomeIcon icon={getSortIconByFieldName('bedraginclbtw')} />
                </th>
                <th className="hand" onClick={sort('btwcategorie')}>
                  Btwcategorie <FontAwesomeIcon icon={getSortIconByFieldName('btwcategorie')} />
                </th>
                <th className="hand" onClick={sort('gemuteerddoor')}>
                  Gemuteerddoor <FontAwesomeIcon icon={getSortIconByFieldName('gemuteerddoor')} />
                </th>
                <th className="hand" onClick={sort('mutatiedatum')}>
                  Mutatiedatum <FontAwesomeIcon icon={getSortIconByFieldName('mutatiedatum')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('periodiek')}>
                  Periodiek <FontAwesomeIcon icon={getSortIconByFieldName('periodiek')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vorderingregelList.map((vorderingregel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vorderingregel/${vorderingregel.id}`} color="link" size="sm">
                      {vorderingregel.id}
                    </Button>
                  </td>
                  <td>{vorderingregel.aangemaaktdoor}</td>
                  <td>
                    {vorderingregel.aanmaakdatum ? (
                      <TextFormat type="date" value={vorderingregel.aanmaakdatum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vorderingregel.bedragexclbtw}</td>
                  <td>{vorderingregel.bedraginclbtw}</td>
                  <td>{vorderingregel.btwcategorie}</td>
                  <td>{vorderingregel.gemuteerddoor}</td>
                  <td>
                    {vorderingregel.mutatiedatum ? (
                      <TextFormat type="date" value={vorderingregel.mutatiedatum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vorderingregel.omschrijving}</td>
                  <td>{vorderingregel.periodiek}</td>
                  <td>{vorderingregel.type}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vorderingregel/${vorderingregel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vorderingregel/${vorderingregel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vorderingregel/${vorderingregel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vorderingregels found</div>
        )}
      </div>
    </div>
  );
};

export default Vorderingregel;
