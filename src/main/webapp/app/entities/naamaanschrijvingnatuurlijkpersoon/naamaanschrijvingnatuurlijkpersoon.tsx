import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './naamaanschrijvingnatuurlijkpersoon.reducer';

export const Naamaanschrijvingnatuurlijkpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const naamaanschrijvingnatuurlijkpersoonList = useAppSelector(state => state.naamaanschrijvingnatuurlijkpersoon.entities);
  const loading = useAppSelector(state => state.naamaanschrijvingnatuurlijkpersoon.loading);

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
      <h2 id="naamaanschrijvingnatuurlijkpersoon-heading" data-cy="NaamaanschrijvingnatuurlijkpersoonHeading">
        Naamaanschrijvingnatuurlijkpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/naamaanschrijvingnatuurlijkpersoon/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Naamaanschrijvingnatuurlijkpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {naamaanschrijvingnatuurlijkpersoonList && naamaanschrijvingnatuurlijkpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanhefaanschrijving')}>
                  Aanhefaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('aanhefaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('geslachtsnaamaanschrijving')}>
                  Geslachtsnaamaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsnaamaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('voorlettersaanschrijving')}>
                  Voorlettersaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('voorlettersaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('voornamenaanschrijving')}>
                  Voornamenaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('voornamenaanschrijving')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {naamaanschrijvingnatuurlijkpersoonList.map((naamaanschrijvingnatuurlijkpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/naamaanschrijvingnatuurlijkpersoon/${naamaanschrijvingnatuurlijkpersoon.id}`}
                      color="link"
                      size="sm"
                    >
                      {naamaanschrijvingnatuurlijkpersoon.id}
                    </Button>
                  </td>
                  <td>{naamaanschrijvingnatuurlijkpersoon.aanhefaanschrijving}</td>
                  <td>{naamaanschrijvingnatuurlijkpersoon.geslachtsnaamaanschrijving}</td>
                  <td>{naamaanschrijvingnatuurlijkpersoon.voorlettersaanschrijving}</td>
                  <td>{naamaanschrijvingnatuurlijkpersoon.voornamenaanschrijving}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/naamaanschrijvingnatuurlijkpersoon/${naamaanschrijvingnatuurlijkpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/naamaanschrijvingnatuurlijkpersoon/${naamaanschrijvingnatuurlijkpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/naamaanschrijvingnatuurlijkpersoon/${naamaanschrijvingnatuurlijkpersoon.id}/delete`)
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
          !loading && <div className="alert alert-warning">No Naamaanschrijvingnatuurlijkpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Naamaanschrijvingnatuurlijkpersoon;
