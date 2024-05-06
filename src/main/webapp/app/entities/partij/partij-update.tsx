import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPartij } from 'app/shared/model/partij.model';
import { getEntity, updateEntity, createEntity, reset } from './partij.reducer';

export const PartijUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const partijEntity = useAppSelector(state => state.partij.entity);
  const loading = useAppSelector(state => state.partij.loading);
  const updating = useAppSelector(state => state.partij.updating);
  const updateSuccess = useAppSelector(state => state.partij.updateSuccess);

  const handleClose = () => {
    navigate('/partij');
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
      ...partijEntity,
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
          ...partijEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.partij.home.createOrEditLabel" data-cy="PartijCreateUpdateHeading">
            Create or edit a Partij
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="partij-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="partij-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Datumaanvanggeldigheidpartij"
                id="partij-datumaanvanggeldigheidpartij"
                name="datumaanvanggeldigheidpartij"
                data-cy="datumaanvanggeldigheidpartij"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidpartij"
                id="partij-datumeindegeldigheidpartij"
                name="datumeindegeldigheidpartij"
                data-cy="datumeindegeldigheidpartij"
                type="date"
              />
              <ValidatedField label="Naam" id="partij-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Soort" id="partij-soort" name="soort" data-cy="soort" type="text" />
              <ValidatedField
                label="Verstrekkingsbeperkingmogelijk"
                id="partij-verstrekkingsbeperkingmogelijk"
                name="verstrekkingsbeperkingmogelijk"
                data-cy="verstrekkingsbeperkingmogelijk"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/partij" replace color="info">
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

export default PartijUpdate;
