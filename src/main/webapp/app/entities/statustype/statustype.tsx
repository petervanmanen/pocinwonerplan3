import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './statustype.reducer';

export const Statustype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const statustypeList = useAppSelector(state => state.statustype.entities);
  const loading = useAppSelector(state => state.statustype.loading);

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
      <h2 id="statustype-heading" data-cy="StatustypeHeading">
        Statustypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/statustype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Statustype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {statustypeList && statustypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidstatustype')}>
                  Datumbegingeldigheidstatustype <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidstatustype')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidstatustype')}>
                  Datumeindegeldigheidstatustype <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidstatustype')} />
                </th>
                <th className="hand" onClick={sort('doorlooptijdstatus')}>
                  Doorlooptijdstatus <FontAwesomeIcon icon={getSortIconByFieldName('doorlooptijdstatus')} />
                </th>
                <th className="hand" onClick={sort('statustypeomschrijving')}>
                  Statustypeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('statustypeomschrijving')} />
                </th>
                <th className="hand" onClick={sort('statustypeomschrijvinggeneriek')}>
                  Statustypeomschrijvinggeneriek <FontAwesomeIcon icon={getSortIconByFieldName('statustypeomschrijvinggeneriek')} />
                </th>
                <th className="hand" onClick={sort('statustypevolgnummer')}>
                  Statustypevolgnummer <FontAwesomeIcon icon={getSortIconByFieldName('statustypevolgnummer')} />
                </th>
                <th>
                  Heeft Zaaktype <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {statustypeList.map((statustype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/statustype/${statustype.id}`} color="link" size="sm">
                      {statustype.id}
                    </Button>
                  </td>
                  <td>{statustype.datumbegingeldigheidstatustype}</td>
                  <td>{statustype.datumeindegeldigheidstatustype}</td>
                  <td>{statustype.doorlooptijdstatus}</td>
                  <td>{statustype.statustypeomschrijving}</td>
                  <td>{statustype.statustypeomschrijvinggeneriek}</td>
                  <td>{statustype.statustypevolgnummer}</td>
                  <td>
                    {statustype.heeftZaaktype ? (
                      <Link to={`/zaaktype/${statustype.heeftZaaktype.id}`}>{statustype.heeftZaaktype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/statustype/${statustype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/statustype/${statustype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/statustype/${statustype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Statustypes found</div>
        )}
      </div>
    </div>
  );
};

export default Statustype;
