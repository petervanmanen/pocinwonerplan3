import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './documenttype.reducer';

export const Documenttype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const documenttypeList = useAppSelector(state => state.documenttype.entities);
  const loading = useAppSelector(state => state.documenttype.loading);

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
      <h2 id="documenttype-heading" data-cy="DocumenttypeHeading">
        Documenttypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/documenttype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Documenttype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documenttypeList && documenttypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheiddocumenttype')}>
                  Datumbegingeldigheiddocumenttype <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheiddocumenttype')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheiddocumenttype')}>
                  Datumeindegeldigheiddocumenttype <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheiddocumenttype')} />
                </th>
                <th className="hand" onClick={sort('documentcategorie')}>
                  Documentcategorie <FontAwesomeIcon icon={getSortIconByFieldName('documentcategorie')} />
                </th>
                <th className="hand" onClick={sort('documenttypeomschrijving')}>
                  Documenttypeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('documenttypeomschrijving')} />
                </th>
                <th className="hand" onClick={sort('documenttypeomschrijvinggeneriek')}>
                  Documenttypeomschrijvinggeneriek <FontAwesomeIcon icon={getSortIconByFieldName('documenttypeomschrijvinggeneriek')} />
                </th>
                <th className="hand" onClick={sort('documenttypetrefwoord')}>
                  Documenttypetrefwoord <FontAwesomeIcon icon={getSortIconByFieldName('documenttypetrefwoord')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documenttypeList.map((documenttype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/documenttype/${documenttype.id}`} color="link" size="sm">
                      {documenttype.id}
                    </Button>
                  </td>
                  <td>{documenttype.datumbegingeldigheiddocumenttype}</td>
                  <td>{documenttype.datumeindegeldigheiddocumenttype}</td>
                  <td>{documenttype.documentcategorie}</td>
                  <td>{documenttype.documenttypeomschrijving}</td>
                  <td>{documenttype.documenttypeomschrijvinggeneriek}</td>
                  <td>{documenttype.documenttypetrefwoord}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/documenttype/${documenttype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/documenttype/${documenttype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/documenttype/${documenttype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Documenttypes found</div>
        )}
      </div>
    </div>
  );
};

export default Documenttype;
