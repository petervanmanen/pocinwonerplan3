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

import { getEntities } from './beschermdestatus.reducer';

export const Beschermdestatus = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const beschermdestatusList = useAppSelector(state => state.beschermdestatus.entities);
  const loading = useAppSelector(state => state.beschermdestatus.loading);

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
      <h2 id="beschermdestatus-heading" data-cy="BeschermdestatusHeading">
        Beschermdestatuses
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/beschermdestatus/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Beschermdestatus
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beschermdestatusList && beschermdestatusList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bronnen')}>
                  Bronnen <FontAwesomeIcon icon={getSortIconByFieldName('bronnen')} />
                </th>
                <th className="hand" onClick={sort('complex')}>
                  Complex <FontAwesomeIcon icon={getSortIconByFieldName('complex')} />
                </th>
                <th className="hand" onClick={sort('datuminschrijvingregister')}>
                  Datuminschrijvingregister <FontAwesomeIcon icon={getSortIconByFieldName('datuminschrijvingregister')} />
                </th>
                <th className="hand" onClick={sort('gemeentelijkmonumentcode')}>
                  Gemeentelijkmonumentcode <FontAwesomeIcon icon={getSortIconByFieldName('gemeentelijkmonumentcode')} />
                </th>
                <th className="hand" onClick={sort('gezichtscode')}>
                  Gezichtscode <FontAwesomeIcon icon={getSortIconByFieldName('gezichtscode')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('rijksmonumentcode')}>
                  Rijksmonumentcode <FontAwesomeIcon icon={getSortIconByFieldName('rijksmonumentcode')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {beschermdestatusList.map((beschermdestatus, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beschermdestatus/${beschermdestatus.id}`} color="link" size="sm">
                      {beschermdestatus.id}
                    </Button>
                  </td>
                  <td>{beschermdestatus.bronnen}</td>
                  <td>{beschermdestatus.complex}</td>
                  <td>
                    {beschermdestatus.datuminschrijvingregister ? (
                      <TextFormat type="date" value={beschermdestatus.datuminschrijvingregister} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{beschermdestatus.gemeentelijkmonumentcode}</td>
                  <td>{beschermdestatus.gezichtscode}</td>
                  <td>{beschermdestatus.naam}</td>
                  <td>{beschermdestatus.omschrijving}</td>
                  <td>{beschermdestatus.opmerkingen}</td>
                  <td>{beschermdestatus.rijksmonumentcode}</td>
                  <td>{beschermdestatus.type}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/beschermdestatus/${beschermdestatus.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/beschermdestatus/${beschermdestatus.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/beschermdestatus/${beschermdestatus.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Beschermdestatuses found</div>
        )}
      </div>
    </div>
  );
};

export default Beschermdestatus;
