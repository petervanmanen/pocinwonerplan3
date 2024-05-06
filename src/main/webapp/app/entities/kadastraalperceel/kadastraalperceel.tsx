import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './kadastraalperceel.reducer';

export const Kadastraalperceel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kadastraalperceelList = useAppSelector(state => state.kadastraalperceel.entities);
  const loading = useAppSelector(state => state.kadastraalperceel.loading);

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
      <h2 id="kadastraalperceel-heading" data-cy="KadastraalperceelHeading">
        Kadastraalperceels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/kadastraalperceel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kadastraalperceel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kadastraalperceelList && kadastraalperceelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanduidingsoortgrootte')}>
                  Aanduidingsoortgrootte <FontAwesomeIcon icon={getSortIconByFieldName('aanduidingsoortgrootte')} />
                </th>
                <th className="hand" onClick={sort('begrenzingperceel')}>
                  Begrenzingperceel <FontAwesomeIcon icon={getSortIconByFieldName('begrenzingperceel')} />
                </th>
                <th className="hand" onClick={sort('grootteperceel')}>
                  Grootteperceel <FontAwesomeIcon icon={getSortIconByFieldName('grootteperceel')} />
                </th>
                <th className="hand" onClick={sort('indicatiedeelperceel')}>
                  Indicatiedeelperceel <FontAwesomeIcon icon={getSortIconByFieldName('indicatiedeelperceel')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingdeelperceel')}>
                  Omschrijvingdeelperceel <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingdeelperceel')} />
                </th>
                <th className="hand" onClick={sort('plaatscoordinatenperceel')}>
                  Plaatscoordinatenperceel <FontAwesomeIcon icon={getSortIconByFieldName('plaatscoordinatenperceel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kadastraalperceelList.map((kadastraalperceel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kadastraalperceel/${kadastraalperceel.id}`} color="link" size="sm">
                      {kadastraalperceel.id}
                    </Button>
                  </td>
                  <td>{kadastraalperceel.aanduidingsoortgrootte}</td>
                  <td>{kadastraalperceel.begrenzingperceel}</td>
                  <td>{kadastraalperceel.grootteperceel}</td>
                  <td>{kadastraalperceel.indicatiedeelperceel}</td>
                  <td>{kadastraalperceel.omschrijvingdeelperceel}</td>
                  <td>{kadastraalperceel.plaatscoordinatenperceel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/kadastraalperceel/${kadastraalperceel.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/kadastraalperceel/${kadastraalperceel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kadastraalperceel/${kadastraalperceel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Kadastraalperceels found</div>
        )}
      </div>
    </div>
  );
};

export default Kadastraalperceel;
