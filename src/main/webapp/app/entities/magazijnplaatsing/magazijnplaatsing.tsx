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

import { getEntities } from './magazijnplaatsing.reducer';

export const Magazijnplaatsing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const magazijnplaatsingList = useAppSelector(state => state.magazijnplaatsing.entities);
  const loading = useAppSelector(state => state.magazijnplaatsing.loading);

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
      <h2 id="magazijnplaatsing-heading" data-cy="MagazijnplaatsingHeading">
        Magazijnplaatsings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/magazijnplaatsing/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Magazijnplaatsing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {magazijnplaatsingList && magazijnplaatsingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('datumgeplaatst')}>
                  Datumgeplaatst <FontAwesomeIcon icon={getSortIconByFieldName('datumgeplaatst')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keydoos')}>
                  Keydoos <FontAwesomeIcon icon={getSortIconByFieldName('keydoos')} />
                </th>
                <th className="hand" onClick={sort('keymagazijnlocatie')}>
                  Keymagazijnlocatie <FontAwesomeIcon icon={getSortIconByFieldName('keymagazijnlocatie')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th className="hand" onClick={sort('uitgeleend')}>
                  Uitgeleend <FontAwesomeIcon icon={getSortIconByFieldName('uitgeleend')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {magazijnplaatsingList.map((magazijnplaatsing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/magazijnplaatsing/${magazijnplaatsing.id}`} color="link" size="sm">
                      {magazijnplaatsing.id}
                    </Button>
                  </td>
                  <td>{magazijnplaatsing.beschrijving}</td>
                  <td>
                    {magazijnplaatsing.datumgeplaatst ? (
                      <TextFormat type="date" value={magazijnplaatsing.datumgeplaatst} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{magazijnplaatsing.herkomst}</td>
                  <td>{magazijnplaatsing.key}</td>
                  <td>{magazijnplaatsing.keydoos}</td>
                  <td>{magazijnplaatsing.keymagazijnlocatie}</td>
                  <td>{magazijnplaatsing.projectcd}</td>
                  <td>{magazijnplaatsing.uitgeleend ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/magazijnplaatsing/${magazijnplaatsing.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/magazijnplaatsing/${magazijnplaatsing.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/magazijnplaatsing/${magazijnplaatsing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Magazijnplaatsings found</div>
        )}
      </div>
    </div>
  );
};

export default Magazijnplaatsing;
