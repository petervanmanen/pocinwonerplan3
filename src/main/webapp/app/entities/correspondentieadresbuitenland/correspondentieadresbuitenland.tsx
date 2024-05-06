import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './correspondentieadresbuitenland.reducer';

export const Correspondentieadresbuitenland = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const correspondentieadresbuitenlandList = useAppSelector(state => state.correspondentieadresbuitenland.entities);
  const loading = useAppSelector(state => state.correspondentieadresbuitenland.loading);

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
      <h2 id="correspondentieadresbuitenland-heading" data-cy="CorrespondentieadresbuitenlandHeading">
        Correspondentieadresbuitenlands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/correspondentieadresbuitenland/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Correspondentieadresbuitenland
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {correspondentieadresbuitenlandList && correspondentieadresbuitenlandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland1')}>
                  Adresbuitenland 1 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland1')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland2')}>
                  Adresbuitenland 2 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland2')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland3')}>
                  Adresbuitenland 3 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland3')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland4')}>
                  Adresbuitenland 4 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland4')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland5')}>
                  Adresbuitenland 5 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland5')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland6')}>
                  Adresbuitenland 6 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland6')} />
                </th>
                <th className="hand" onClick={sort('landcorrespondentieadres')}>
                  Landcorrespondentieadres <FontAwesomeIcon icon={getSortIconByFieldName('landcorrespondentieadres')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {correspondentieadresbuitenlandList.map((correspondentieadresbuitenland, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/correspondentieadresbuitenland/${correspondentieadresbuitenland.id}`} color="link" size="sm">
                      {correspondentieadresbuitenland.id}
                    </Button>
                  </td>
                  <td>{correspondentieadresbuitenland.adresbuitenland1}</td>
                  <td>{correspondentieadresbuitenland.adresbuitenland2}</td>
                  <td>{correspondentieadresbuitenland.adresbuitenland3}</td>
                  <td>{correspondentieadresbuitenland.adresbuitenland4}</td>
                  <td>{correspondentieadresbuitenland.adresbuitenland5}</td>
                  <td>{correspondentieadresbuitenland.adresbuitenland6}</td>
                  <td>{correspondentieadresbuitenland.landcorrespondentieadres}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/correspondentieadresbuitenland/${correspondentieadresbuitenland.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/correspondentieadresbuitenland/${correspondentieadresbuitenland.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/correspondentieadresbuitenland/${correspondentieadresbuitenland.id}/delete`)
                        }
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
          !loading && <div className="alert alert-warning">No Correspondentieadresbuitenlands found</div>
        )}
      </div>
    </div>
  );
};

export default Correspondentieadresbuitenland;
