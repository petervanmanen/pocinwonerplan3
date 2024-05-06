import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './aanvraaginkooporder.reducer';

export const Aanvraaginkooporder = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aanvraaginkooporderList = useAppSelector(state => state.aanvraaginkooporder.entities);
  const loading = useAppSelector(state => state.aanvraaginkooporder.loading);

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
      <h2 id="aanvraaginkooporder-heading" data-cy="AanvraaginkooporderHeading">
        Aanvraaginkooporders
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/aanvraaginkooporder/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aanvraaginkooporder
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aanvraaginkooporderList && aanvraaginkooporderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('betalingovermeerjaren')}>
                  Betalingovermeerjaren <FontAwesomeIcon icon={getSortIconByFieldName('betalingovermeerjaren')} />
                </th>
                <th className="hand" onClick={sort('correspondentienummer')}>
                  Correspondentienummer <FontAwesomeIcon icon={getSortIconByFieldName('correspondentienummer')} />
                </th>
                <th className="hand" onClick={sort('inhuuranders')}>
                  Inhuuranders <FontAwesomeIcon icon={getSortIconByFieldName('inhuuranders')} />
                </th>
                <th className="hand" onClick={sort('leveringofdienst')}>
                  Leveringofdienst <FontAwesomeIcon icon={getSortIconByFieldName('leveringofdienst')} />
                </th>
                <th className="hand" onClick={sort('nettototaalbedrag')}>
                  Nettototaalbedrag <FontAwesomeIcon icon={getSortIconByFieldName('nettototaalbedrag')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('onderwerp')}>
                  Onderwerp <FontAwesomeIcon icon={getSortIconByFieldName('onderwerp')} />
                </th>
                <th className="hand" onClick={sort('reactie')}>
                  Reactie <FontAwesomeIcon icon={getSortIconByFieldName('reactie')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('wijzevaninhuur')}>
                  Wijzevaninhuur <FontAwesomeIcon icon={getSortIconByFieldName('wijzevaninhuur')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aanvraaginkooporderList.map((aanvraaginkooporder, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aanvraaginkooporder/${aanvraaginkooporder.id}`} color="link" size="sm">
                      {aanvraaginkooporder.id}
                    </Button>
                  </td>
                  <td>{aanvraaginkooporder.betalingovermeerjaren}</td>
                  <td>{aanvraaginkooporder.correspondentienummer}</td>
                  <td>{aanvraaginkooporder.inhuuranders}</td>
                  <td>{aanvraaginkooporder.leveringofdienst}</td>
                  <td>{aanvraaginkooporder.nettototaalbedrag}</td>
                  <td>{aanvraaginkooporder.omschrijving}</td>
                  <td>{aanvraaginkooporder.onderwerp}</td>
                  <td>{aanvraaginkooporder.reactie}</td>
                  <td>{aanvraaginkooporder.status}</td>
                  <td>{aanvraaginkooporder.wijzevaninhuur}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/aanvraaginkooporder/${aanvraaginkooporder.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aanvraaginkooporder/${aanvraaginkooporder.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aanvraaginkooporder/${aanvraaginkooporder.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aanvraaginkooporders found</div>
        )}
      </div>
    </div>
  );
};

export default Aanvraaginkooporder;
