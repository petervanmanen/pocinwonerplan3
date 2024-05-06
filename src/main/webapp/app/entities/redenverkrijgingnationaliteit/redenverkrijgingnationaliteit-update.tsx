import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRedenverkrijgingnationaliteit } from 'app/shared/model/redenverkrijgingnationaliteit.model';
import { getEntity, updateEntity, createEntity, reset } from './redenverkrijgingnationaliteit.reducer';

export const RedenverkrijgingnationaliteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const redenverkrijgingnationaliteitEntity = useAppSelector(state => state.redenverkrijgingnationaliteit.entity);
  const loading = useAppSelector(state => state.redenverkrijgingnationaliteit.loading);
  const updating = useAppSelector(state => state.redenverkrijgingnationaliteit.updating);
  const updateSuccess = useAppSelector(state => state.redenverkrijgingnationaliteit.updateSuccess);

  const handleClose = () => {
    navigate('/redenverkrijgingnationaliteit');
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
      ...redenverkrijgingnationaliteitEntity,
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
          ...redenverkrijgingnationaliteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.redenverkrijgingnationaliteit.home.createOrEditLabel" data-cy="RedenverkrijgingnationaliteitCreateUpdateHeading">
            Create or edit a Redenverkrijgingnationaliteit
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
                  id="redenverkrijgingnationaliteit-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Datumaanvanggeldigheidverkrijging"
                id="redenverkrijgingnationaliteit-datumaanvanggeldigheidverkrijging"
                name="datumaanvanggeldigheidverkrijging"
                data-cy="datumaanvanggeldigheidverkrijging"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidverkrijging"
                id="redenverkrijgingnationaliteit-datumeindegeldigheidverkrijging"
                name="datumeindegeldigheidverkrijging"
                data-cy="datumeindegeldigheidverkrijging"
                type="date"
              />
              <ValidatedField
                label="Omschrijvingverkrijging"
                id="redenverkrijgingnationaliteit-omschrijvingverkrijging"
                name="omschrijvingverkrijging"
                data-cy="omschrijvingverkrijging"
                type="text"
              />
              <ValidatedField
                label="Redennummerverkrijging"
                id="redenverkrijgingnationaliteit-redennummerverkrijging"
                name="redennummerverkrijging"
                data-cy="redennummerverkrijging"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/redenverkrijgingnationaliteit"
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

export default RedenverkrijgingnationaliteitUpdate;
