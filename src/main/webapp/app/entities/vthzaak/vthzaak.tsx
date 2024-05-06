import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vthzaak.reducer';

export const Vthzaak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vthzaakList = useAppSelector(state => state.vthzaak.entities);
  const loading = useAppSelector(state => state.vthzaak.loading);

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
      <h2 id="vthzaak-heading" data-cy="VthzaakHeading">
        Vthzaaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vthzaak/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vthzaak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vthzaakList && vthzaakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('behandelaar')}>
                  Behandelaar <FontAwesomeIcon icon={getSortIconByFieldName('behandelaar')} />
                </th>
                <th className="hand" onClick={sort('bevoegdgezag')}>
                  Bevoegdgezag <FontAwesomeIcon icon={getSortIconByFieldName('bevoegdgezag')} />
                </th>
                <th className="hand" onClick={sort('prioritering')}>
                  Prioritering <FontAwesomeIcon icon={getSortIconByFieldName('prioritering')} />
                </th>
                <th className="hand" onClick={sort('teambehandelaar')}>
                  Teambehandelaar <FontAwesomeIcon icon={getSortIconByFieldName('teambehandelaar')} />
                </th>
                <th className="hand" onClick={sort('uitvoerendeinstantie')}>
                  Uitvoerendeinstantie <FontAwesomeIcon icon={getSortIconByFieldName('uitvoerendeinstantie')} />
                </th>
                <th className="hand" onClick={sort('verkamering')}>
                  Verkamering <FontAwesomeIcon icon={getSortIconByFieldName('verkamering')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vthzaakList.map((vthzaak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vthzaak/${vthzaak.id}`} color="link" size="sm">
                      {vthzaak.id}
                    </Button>
                  </td>
                  <td>{vthzaak.behandelaar}</td>
                  <td>{vthzaak.bevoegdgezag}</td>
                  <td>{vthzaak.prioritering}</td>
                  <td>{vthzaak.teambehandelaar}</td>
                  <td>{vthzaak.uitvoerendeinstantie}</td>
                  <td>{vthzaak.verkamering}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vthzaak/${vthzaak.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vthzaak/${vthzaak.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vthzaak/${vthzaak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vthzaaks found</div>
        )}
      </div>
    </div>
  );
};

export default Vthzaak;
