import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './naamgebruiknatuurlijkpersoon.reducer';

export const Naamgebruiknatuurlijkpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const naamgebruiknatuurlijkpersoonList = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.entities);
  const loading = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.loading);

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
      <h2 id="naamgebruiknatuurlijkpersoon-heading" data-cy="NaamgebruiknatuurlijkpersoonHeading">
        Naamgebruiknatuurlijkpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/naamgebruiknatuurlijkpersoon/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Naamgebruiknatuurlijkpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {naamgebruiknatuurlijkpersoonList && naamgebruiknatuurlijkpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanhefaanschrijving')}>
                  Aanhefaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('aanhefaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('adellijketitelnaamgebruik')}>
                  Adellijketitelnaamgebruik <FontAwesomeIcon icon={getSortIconByFieldName('adellijketitelnaamgebruik')} />
                </th>
                <th className="hand" onClick={sort('geslachtsnaamstamnaamgebruik')}>
                  Geslachtsnaamstamnaamgebruik <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsnaamstamnaamgebruik')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {naamgebruiknatuurlijkpersoonList.map((naamgebruiknatuurlijkpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/naamgebruiknatuurlijkpersoon/${naamgebruiknatuurlijkpersoon.id}`} color="link" size="sm">
                      {naamgebruiknatuurlijkpersoon.id}
                    </Button>
                  </td>
                  <td>{naamgebruiknatuurlijkpersoon.aanhefaanschrijving}</td>
                  <td>{naamgebruiknatuurlijkpersoon.adellijketitelnaamgebruik}</td>
                  <td>{naamgebruiknatuurlijkpersoon.geslachtsnaamstamnaamgebruik}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/naamgebruiknatuurlijkpersoon/${naamgebruiknatuurlijkpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/naamgebruiknatuurlijkpersoon/${naamgebruiknatuurlijkpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/naamgebruiknatuurlijkpersoon/${naamgebruiknatuurlijkpersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Naamgebruiknatuurlijkpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Naamgebruiknatuurlijkpersoon;
