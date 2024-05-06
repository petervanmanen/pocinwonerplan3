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

import { getEntities } from './versie.reducer';

export const Versie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const versieList = useAppSelector(state => state.versie.entities);
  const loading = useAppSelector(state => state.versie.loading);

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
      <h2 id="versie-heading" data-cy="VersieHeading">
        Versies
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/versie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Versie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {versieList && versieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantal')}>
                  Aantal <FontAwesomeIcon icon={getSortIconByFieldName('aantal')} />
                </th>
                <th className="hand" onClick={sort('datumeindesupport')}>
                  Datumeindesupport <FontAwesomeIcon icon={getSortIconByFieldName('datumeindesupport')} />
                </th>
                <th className="hand" onClick={sort('kosten')}>
                  Kosten <FontAwesomeIcon icon={getSortIconByFieldName('kosten')} />
                </th>
                <th className="hand" onClick={sort('licentie')}>
                  Licentie <FontAwesomeIcon icon={getSortIconByFieldName('licentie')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('versienummer')}>
                  Versienummer <FontAwesomeIcon icon={getSortIconByFieldName('versienummer')} />
                </th>
                <th>
                  Heeftversies Applicatie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {versieList.map((versie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/versie/${versie.id}`} color="link" size="sm">
                      {versie.id}
                    </Button>
                  </td>
                  <td>{versie.aantal}</td>
                  <td>
                    {versie.datumeindesupport ? (
                      <TextFormat type="date" value={versie.datumeindesupport} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{versie.kosten}</td>
                  <td>{versie.licentie}</td>
                  <td>{versie.status}</td>
                  <td>{versie.versienummer}</td>
                  <td>
                    {versie.heeftversiesApplicatie ? (
                      <Link to={`/applicatie/${versie.heeftversiesApplicatie.id}`}>{versie.heeftversiesApplicatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/versie/${versie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/versie/${versie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/versie/${versie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Versies found</div>
        )}
      </div>
    </div>
  );
};

export default Versie;
