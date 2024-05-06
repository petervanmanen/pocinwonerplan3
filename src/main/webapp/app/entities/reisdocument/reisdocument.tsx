import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './reisdocument.reducer';

export const Reisdocument = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const reisdocumentList = useAppSelector(state => state.reisdocument.entities);
  const loading = useAppSelector(state => state.reisdocument.loading);

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
      <h2 id="reisdocument-heading" data-cy="ReisdocumentHeading">
        Reisdocuments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/reisdocument/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Reisdocument
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reisdocumentList && reisdocumentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanduidinginhoudingvermissing')}>
                  Aanduidinginhoudingvermissing <FontAwesomeIcon icon={getSortIconByFieldName('aanduidinginhoudingvermissing')} />
                </th>
                <th className="hand" onClick={sort('autoriteitvanafgifte')}>
                  Autoriteitvanafgifte <FontAwesomeIcon icon={getSortIconByFieldName('autoriteitvanafgifte')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheiddocument')}>
                  Datumeindegeldigheiddocument <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheiddocument')} />
                </th>
                <th className="hand" onClick={sort('datumingangdocument')}>
                  Datumingangdocument <FontAwesomeIcon icon={getSortIconByFieldName('datumingangdocument')} />
                </th>
                <th className="hand" onClick={sort('datuminhoudingofvermissing')}>
                  Datuminhoudingofvermissing <FontAwesomeIcon icon={getSortIconByFieldName('datuminhoudingofvermissing')} />
                </th>
                <th className="hand" onClick={sort('datumuitgifte')}>
                  Datumuitgifte <FontAwesomeIcon icon={getSortIconByFieldName('datumuitgifte')} />
                </th>
                <th className="hand" onClick={sort('reisdocumentnummer')}>
                  Reisdocumentnummer <FontAwesomeIcon icon={getSortIconByFieldName('reisdocumentnummer')} />
                </th>
                <th className="hand" onClick={sort('soort')}>
                  Soort <FontAwesomeIcon icon={getSortIconByFieldName('soort')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reisdocumentList.map((reisdocument, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reisdocument/${reisdocument.id}`} color="link" size="sm">
                      {reisdocument.id}
                    </Button>
                  </td>
                  <td>{reisdocument.aanduidinginhoudingvermissing}</td>
                  <td>{reisdocument.autoriteitvanafgifte}</td>
                  <td>{reisdocument.datumeindegeldigheiddocument}</td>
                  <td>{reisdocument.datumingangdocument}</td>
                  <td>{reisdocument.datuminhoudingofvermissing}</td>
                  <td>{reisdocument.datumuitgifte}</td>
                  <td>{reisdocument.reisdocumentnummer}</td>
                  <td>{reisdocument.soort}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/reisdocument/${reisdocument.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/reisdocument/${reisdocument.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/reisdocument/${reisdocument.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Reisdocuments found</div>
        )}
      </div>
    </div>
  );
};

export default Reisdocument;
