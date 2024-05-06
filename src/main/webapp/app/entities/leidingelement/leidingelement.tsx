import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './leidingelement.reducer';

export const Leidingelement = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const leidingelementList = useAppSelector(state => state.leidingelement.entities);
  const loading = useAppSelector(state => state.leidingelement.loading);

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
      <h2 id="leidingelement-heading" data-cy="LeidingelementHeading">
        Leidingelements
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/leidingelement/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leidingelement
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leidingelementList && leidingelementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('afwijkendedieptelegging')}>
                  Afwijkendedieptelegging <FontAwesomeIcon icon={getSortIconByFieldName('afwijkendedieptelegging')} />
                </th>
                <th className="hand" onClick={sort('diepte')}>
                  Diepte <FontAwesomeIcon icon={getSortIconByFieldName('diepte')} />
                </th>
                <th className="hand" onClick={sort('geonauwkeurigheidxy')}>
                  Geonauwkeurigheidxy <FontAwesomeIcon icon={getSortIconByFieldName('geonauwkeurigheidxy')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
                </th>
                <th className="hand" onClick={sort('leverancier')}>
                  Leverancier <FontAwesomeIcon icon={getSortIconByFieldName('leverancier')} />
                </th>
                <th className="hand" onClick={sort('themaimkl')}>
                  Themaimkl <FontAwesomeIcon icon={getSortIconByFieldName('themaimkl')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leidingelementList.map((leidingelement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leidingelement/${leidingelement.id}`} color="link" size="sm">
                      {leidingelement.id}
                    </Button>
                  </td>
                  <td>{leidingelement.afwijkendedieptelegging}</td>
                  <td>{leidingelement.diepte}</td>
                  <td>{leidingelement.geonauwkeurigheidxy}</td>
                  <td>{leidingelement.jaaronderhouduitgevoerd}</td>
                  <td>{leidingelement.leverancier}</td>
                  <td>{leidingelement.themaimkl}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/leidingelement/${leidingelement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leidingelement/${leidingelement.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/leidingelement/${leidingelement.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Leidingelements found</div>
        )}
      </div>
    </div>
  );
};

export default Leidingelement;
