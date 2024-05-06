import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './verblijfbuitenland.reducer';

export const Verblijfbuitenland = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verblijfbuitenlandList = useAppSelector(state => state.verblijfbuitenland.entities);
  const loading = useAppSelector(state => state.verblijfbuitenland.loading);

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
      <h2 id="verblijfbuitenland-heading" data-cy="VerblijfbuitenlandHeading">
        Verblijfbuitenlands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/verblijfbuitenland/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verblijfbuitenland
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verblijfbuitenlandList && verblijfbuitenlandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland1')}>
                  Adresregelbuitenland 1 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland1')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland2')}>
                  Adresregelbuitenland 2 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland2')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland3')}>
                  Adresregelbuitenland 3 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland3')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland4')}>
                  Adresregelbuitenland 4 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland4')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland5')}>
                  Adresregelbuitenland 5 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland5')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland6')}>
                  Adresregelbuitenland 6 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland6')} />
                </th>
                <th className="hand" onClick={sort('landofgebiedverblijfadres')}>
                  Landofgebiedverblijfadres <FontAwesomeIcon icon={getSortIconByFieldName('landofgebiedverblijfadres')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verblijfbuitenlandList.map((verblijfbuitenland, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verblijfbuitenland/${verblijfbuitenland.id}`} color="link" size="sm">
                      {verblijfbuitenland.id}
                    </Button>
                  </td>
                  <td>{verblijfbuitenland.adresregelbuitenland1}</td>
                  <td>{verblijfbuitenland.adresregelbuitenland2}</td>
                  <td>{verblijfbuitenland.adresregelbuitenland3}</td>
                  <td>{verblijfbuitenland.adresregelbuitenland4}</td>
                  <td>{verblijfbuitenland.adresregelbuitenland5}</td>
                  <td>{verblijfbuitenland.adresregelbuitenland6}</td>
                  <td>{verblijfbuitenland.landofgebiedverblijfadres}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/verblijfbuitenland/${verblijfbuitenland.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verblijfbuitenland/${verblijfbuitenland.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verblijfbuitenland/${verblijfbuitenland.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verblijfbuitenlands found</div>
        )}
      </div>
    </div>
  );
};

export default Verblijfbuitenland;
