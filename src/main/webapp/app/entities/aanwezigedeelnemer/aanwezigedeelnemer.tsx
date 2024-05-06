import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './aanwezigedeelnemer.reducer';

export const Aanwezigedeelnemer = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aanwezigedeelnemerList = useAppSelector(state => state.aanwezigedeelnemer.entities);
  const loading = useAppSelector(state => state.aanwezigedeelnemer.loading);

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
      <h2 id="aanwezigedeelnemer-heading" data-cy="AanwezigedeelnemerHeading">
        Aanwezigedeelnemers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/aanwezigedeelnemer/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aanwezigedeelnemer
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aanwezigedeelnemerList && aanwezigedeelnemerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanvangaanwezigheid')}>
                  Aanvangaanwezigheid <FontAwesomeIcon icon={getSortIconByFieldName('aanvangaanwezigheid')} />
                </th>
                <th className="hand" onClick={sort('eindeaanwezigheid')}>
                  Eindeaanwezigheid <FontAwesomeIcon icon={getSortIconByFieldName('eindeaanwezigheid')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('rol')}>
                  Rol <FontAwesomeIcon icon={getSortIconByFieldName('rol')} />
                </th>
                <th className="hand" onClick={sort('vertegenwoordigtorganisatie')}>
                  Vertegenwoordigtorganisatie <FontAwesomeIcon icon={getSortIconByFieldName('vertegenwoordigtorganisatie')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aanwezigedeelnemerList.map((aanwezigedeelnemer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aanwezigedeelnemer/${aanwezigedeelnemer.id}`} color="link" size="sm">
                      {aanwezigedeelnemer.id}
                    </Button>
                  </td>
                  <td>{aanwezigedeelnemer.aanvangaanwezigheid}</td>
                  <td>{aanwezigedeelnemer.eindeaanwezigheid}</td>
                  <td>{aanwezigedeelnemer.naam}</td>
                  <td>{aanwezigedeelnemer.rol}</td>
                  <td>{aanwezigedeelnemer.vertegenwoordigtorganisatie}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/aanwezigedeelnemer/${aanwezigedeelnemer.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aanwezigedeelnemer/${aanwezigedeelnemer.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aanwezigedeelnemer/${aanwezigedeelnemer.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aanwezigedeelnemers found</div>
        )}
      </div>
    </div>
  );
};

export default Aanwezigedeelnemer;
