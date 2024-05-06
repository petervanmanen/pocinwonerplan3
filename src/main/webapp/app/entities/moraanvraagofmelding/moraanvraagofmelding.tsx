import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './moraanvraagofmelding.reducer';

export const Moraanvraagofmelding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const moraanvraagofmeldingList = useAppSelector(state => state.moraanvraagofmelding.entities);
  const loading = useAppSelector(state => state.moraanvraagofmelding.loading);

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
      <h2 id="moraanvraagofmelding-heading" data-cy="MoraanvraagofmeldingHeading">
        Moraanvraagofmeldings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/moraanvraagofmelding/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Moraanvraagofmelding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {moraanvraagofmeldingList && moraanvraagofmeldingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('crow')}>
                  Crow <FontAwesomeIcon icon={getSortIconByFieldName('crow')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('locatieomschrijving')}>
                  Locatieomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('locatieomschrijving')} />
                </th>
                <th className="hand" onClick={sort('meldingomschrijving')}>
                  Meldingomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('meldingomschrijving')} />
                </th>
                <th className="hand" onClick={sort('meldingtekst')}>
                  Meldingtekst <FontAwesomeIcon icon={getSortIconByFieldName('meldingtekst')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {moraanvraagofmeldingList.map((moraanvraagofmelding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/moraanvraagofmelding/${moraanvraagofmelding.id}`} color="link" size="sm">
                      {moraanvraagofmelding.id}
                    </Button>
                  </td>
                  <td>{moraanvraagofmelding.crow}</td>
                  <td>{moraanvraagofmelding.locatie}</td>
                  <td>{moraanvraagofmelding.locatieomschrijving}</td>
                  <td>{moraanvraagofmelding.meldingomschrijving}</td>
                  <td>{moraanvraagofmelding.meldingtekst}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/moraanvraagofmelding/${moraanvraagofmelding.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/moraanvraagofmelding/${moraanvraagofmelding.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/moraanvraagofmelding/${moraanvraagofmelding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Moraanvraagofmeldings found</div>
        )}
      </div>
    </div>
  );
};

export default Moraanvraagofmelding;
