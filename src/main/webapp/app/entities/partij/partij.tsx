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

import { getEntities } from './partij.reducer';

export const Partij = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const partijList = useAppSelector(state => state.partij.entities);
  const loading = useAppSelector(state => state.partij.loading);

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
      <h2 id="partij-heading" data-cy="PartijHeading">
        Partijs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/partij/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Partij
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {partijList && partijList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('datumaanvanggeldigheidpartij')}>
                  Datumaanvanggeldigheidpartij <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvanggeldigheidpartij')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidpartij')}>
                  Datumeindegeldigheidpartij <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidpartij')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('soort')}>
                  Soort <FontAwesomeIcon icon={getSortIconByFieldName('soort')} />
                </th>
                <th className="hand" onClick={sort('verstrekkingsbeperkingmogelijk')}>
                  Verstrekkingsbeperkingmogelijk <FontAwesomeIcon icon={getSortIconByFieldName('verstrekkingsbeperkingmogelijk')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {partijList.map((partij, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/partij/${partij.id}`} color="link" size="sm">
                      {partij.id}
                    </Button>
                  </td>
                  <td>{partij.code}</td>
                  <td>
                    {partij.datumaanvanggeldigheidpartij ? (
                      <TextFormat type="date" value={partij.datumaanvanggeldigheidpartij} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {partij.datumeindegeldigheidpartij ? (
                      <TextFormat type="date" value={partij.datumeindegeldigheidpartij} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{partij.naam}</td>
                  <td>{partij.soort}</td>
                  <td>{partij.verstrekkingsbeperkingmogelijk}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/partij/${partij.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/partij/${partij.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/partij/${partij.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Partijs found</div>
        )}
      </div>
    </div>
  );
};

export default Partij;
