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

import { getEntities } from './sollicitatiegesprek.reducer';

export const Sollicitatiegesprek = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const sollicitatiegesprekList = useAppSelector(state => state.sollicitatiegesprek.entities);
  const loading = useAppSelector(state => state.sollicitatiegesprek.loading);

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
      <h2 id="sollicitatiegesprek-heading" data-cy="SollicitatiegesprekHeading">
        Sollicitatiegespreks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/sollicitatiegesprek/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Sollicitatiegesprek
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sollicitatiegesprekList && sollicitatiegesprekList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangenomen')}>
                  Aangenomen <FontAwesomeIcon icon={getSortIconByFieldName('aangenomen')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('volgendgesprek')}>
                  Volgendgesprek <FontAwesomeIcon icon={getSortIconByFieldName('volgendgesprek')} />
                </th>
                <th>
                  Inkadervan Sollicitatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Kandidaat Sollicitant <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Doetsollicitatiegesprek Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sollicitatiegesprekList.map((sollicitatiegesprek, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sollicitatiegesprek/${sollicitatiegesprek.id}`} color="link" size="sm">
                      {sollicitatiegesprek.id}
                    </Button>
                  </td>
                  <td>{sollicitatiegesprek.aangenomen ? 'true' : 'false'}</td>
                  <td>
                    {sollicitatiegesprek.datum ? (
                      <TextFormat type="date" value={sollicitatiegesprek.datum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{sollicitatiegesprek.opmerkingen}</td>
                  <td>{sollicitatiegesprek.volgendgesprek ? 'true' : 'false'}</td>
                  <td>
                    {sollicitatiegesprek.inkadervanSollicitatie ? (
                      <Link to={`/sollicitatie/${sollicitatiegesprek.inkadervanSollicitatie.id}`}>
                        {sollicitatiegesprek.inkadervanSollicitatie.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {sollicitatiegesprek.kandidaatSollicitants
                      ? sollicitatiegesprek.kandidaatSollicitants.map((val, j) => (
                          <span key={j}>
                            <Link to={`/sollicitant/${val.id}`}>{val.id}</Link>
                            {j === sollicitatiegesprek.kandidaatSollicitants.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {sollicitatiegesprek.doetsollicitatiegesprekWerknemers
                      ? sollicitatiegesprek.doetsollicitatiegesprekWerknemers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/werknemer/${val.id}`}>{val.id}</Link>
                            {j === sollicitatiegesprek.doetsollicitatiegesprekWerknemers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/sollicitatiegesprek/${sollicitatiegesprek.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sollicitatiegesprek/${sollicitatiegesprek.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/sollicitatiegesprek/${sollicitatiegesprek.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Sollicitatiegespreks found</div>
        )}
      </div>
    </div>
  );
};

export default Sollicitatiegesprek;
