import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './ontbindinghuwelijkgeregistreerdpartnerschap.reducer';

export const Ontbindinghuwelijkgeregistreerdpartnerschap = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ontbindinghuwelijkgeregistreerdpartnerschapList = useAppSelector(
    state => state.ontbindinghuwelijkgeregistreerdpartnerschap.entities,
  );
  const loading = useAppSelector(state => state.ontbindinghuwelijkgeregistreerdpartnerschap.loading);

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
      <h2 id="ontbindinghuwelijkgeregistreerdpartnerschap-heading" data-cy="OntbindinghuwelijkgeregistreerdpartnerschapHeading">
        Ontbindinghuwelijkgeregistreerdpartnerschaps
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/ontbindinghuwelijkgeregistreerdpartnerschap/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ontbindinghuwelijkgeregistreerdpartnerschap
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ontbindinghuwelijkgeregistreerdpartnerschapList && ontbindinghuwelijkgeregistreerdpartnerschapList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('buitenlandseplaatseinde')}>
                  Buitenlandseplaatseinde <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandseplaatseinde')} />
                </th>
                <th className="hand" onClick={sort('buitenlandseregioeinde')}>
                  Buitenlandseregioeinde <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandseregioeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('gemeenteeinde')}>
                  Gemeenteeinde <FontAwesomeIcon icon={getSortIconByFieldName('gemeenteeinde')} />
                </th>
                <th className="hand" onClick={sort('landofgebiedeinde')}>
                  Landofgebiedeinde <FontAwesomeIcon icon={getSortIconByFieldName('landofgebiedeinde')} />
                </th>
                <th className="hand" onClick={sort('omschrijvinglocatieeinde')}>
                  Omschrijvinglocatieeinde <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvinglocatieeinde')} />
                </th>
                <th className="hand" onClick={sort('redeneinde')}>
                  Redeneinde <FontAwesomeIcon icon={getSortIconByFieldName('redeneinde')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ontbindinghuwelijkgeregistreerdpartnerschapList.map((ontbindinghuwelijkgeregistreerdpartnerschap, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/ontbindinghuwelijkgeregistreerdpartnerschap/${ontbindinghuwelijkgeregistreerdpartnerschap.id}`}
                      color="link"
                      size="sm"
                    >
                      {ontbindinghuwelijkgeregistreerdpartnerschap.id}
                    </Button>
                  </td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.buitenlandseplaatseinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.buitenlandseregioeinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.datumeinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.gemeenteeinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.landofgebiedeinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.omschrijvinglocatieeinde}</td>
                  <td>{ontbindinghuwelijkgeregistreerdpartnerschap.redeneinde}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/ontbindinghuwelijkgeregistreerdpartnerschap/${ontbindinghuwelijkgeregistreerdpartnerschap.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ontbindinghuwelijkgeregistreerdpartnerschap/${ontbindinghuwelijkgeregistreerdpartnerschap.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/ontbindinghuwelijkgeregistreerdpartnerschap/${ontbindinghuwelijkgeregistreerdpartnerschap.id}/delete`)
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
          !loading && <div className="alert alert-warning">No Ontbindinghuwelijkgeregistreerdpartnerschaps found</div>
        )}
      </div>
    </div>
  );
};

export default Ontbindinghuwelijkgeregistreerdpartnerschap;
