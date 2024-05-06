import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.reducer';

export const Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const sluitingofaangaanhuwelijkofgeregistreerdpartnerschapList = useAppSelector(
    state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.entities,
  );
  const loading = useAppSelector(state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.loading);

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
      <h2
        id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-heading"
        data-cy="SluitingofaangaanhuwelijkofgeregistreerdpartnerschapHeading"
      >
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sluitingofaangaanhuwelijkofgeregistreerdpartnerschapList && sluitingofaangaanhuwelijkofgeregistreerdpartnerschapList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('buitenlandseplaatsaanvang')}>
                  Buitenlandseplaatsaanvang <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandseplaatsaanvang')} />
                </th>
                <th className="hand" onClick={sort('buitenlandseregioaanvang')}>
                  Buitenlandseregioaanvang <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandseregioaanvang')} />
                </th>
                <th className="hand" onClick={sort('datumaanvang')}>
                  Datumaanvang <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvang')} />
                </th>
                <th className="hand" onClick={sort('gemeenteaanvang')}>
                  Gemeenteaanvang <FontAwesomeIcon icon={getSortIconByFieldName('gemeenteaanvang')} />
                </th>
                <th className="hand" onClick={sort('landofgebiedaanvang')}>
                  Landofgebiedaanvang <FontAwesomeIcon icon={getSortIconByFieldName('landofgebiedaanvang')} />
                </th>
                <th className="hand" onClick={sort('omschrijvinglocatieaanvang')}>
                  Omschrijvinglocatieaanvang <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvinglocatieaanvang')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sluitingofaangaanhuwelijkofgeregistreerdpartnerschapList.map((sluitingofaangaanhuwelijkofgeregistreerdpartnerschap, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/${sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.id}`}
                      color="link"
                      size="sm"
                    >
                      {sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.id}
                    </Button>
                  </td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.buitenlandseplaatsaanvang}</td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.buitenlandseregioaanvang}</td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.datumaanvang}</td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.gemeenteaanvang}</td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.landofgebiedaanvang}</td>
                  <td>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.omschrijvinglocatieaanvang}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/${sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/${sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/${sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.id}/delete`)
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
          !loading && <div className="alert alert-warning">No Sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps found</div>
        )}
      </div>
    </div>
  );
};

export default Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
