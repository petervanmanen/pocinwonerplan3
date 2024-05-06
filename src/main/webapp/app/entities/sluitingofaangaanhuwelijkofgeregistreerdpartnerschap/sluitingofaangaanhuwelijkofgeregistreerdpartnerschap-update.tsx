import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap } from 'app/shared/model/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.model';
import { getEntity, updateEntity, createEntity, reset } from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.reducer';

export const SluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.entity,
  );
  const loading = useAppSelector(state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.loading);
  const updating = useAppSelector(state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.updating);
  const updateSuccess = useAppSelector(state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.updateSuccess);

  const handleClose = () => {
    navigate('/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.home.createOrEditLabel"
            data-cy="SluitingofaangaanhuwelijkofgeregistreerdpartnerschapCreateUpdateHeading"
          >
            Create or edit a Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Buitenlandseplaatsaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-buitenlandseplaatsaanvang"
                name="buitenlandseplaatsaanvang"
                data-cy="buitenlandseplaatsaanvang"
                type="text"
              />
              <ValidatedField
                label="Buitenlandseregioaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-buitenlandseregioaanvang"
                name="buitenlandseregioaanvang"
                data-cy="buitenlandseregioaanvang"
                type="text"
              />
              <ValidatedField
                label="Datumaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-datumaanvang"
                name="datumaanvang"
                data-cy="datumaanvang"
                type="text"
              />
              <ValidatedField
                label="Gemeenteaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-gemeenteaanvang"
                name="gemeenteaanvang"
                data-cy="gemeenteaanvang"
                type="text"
              />
              <ValidatedField
                label="Landofgebiedaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-landofgebiedaanvang"
                name="landofgebiedaanvang"
                data-cy="landofgebiedaanvang"
                type="text"
              />
              <ValidatedField
                label="Omschrijvinglocatieaanvang"
                id="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap-omschrijvinglocatieaanvang"
                name="omschrijvinglocatieaanvang"
                data-cy="omschrijvinglocatieaanvang"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdate;
