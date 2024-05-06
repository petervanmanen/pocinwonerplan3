import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGenotenopleiding } from 'app/shared/model/genotenopleiding.model';
import { getEntity, updateEntity, createEntity, reset } from './genotenopleiding.reducer';

export const GenotenopleidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const genotenopleidingEntity = useAppSelector(state => state.genotenopleiding.entity);
  const loading = useAppSelector(state => state.genotenopleiding.loading);
  const updating = useAppSelector(state => state.genotenopleiding.updating);
  const updateSuccess = useAppSelector(state => state.genotenopleiding.updateSuccess);

  const handleClose = () => {
    navigate('/genotenopleiding');
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
    if (values.prijs !== undefined && typeof values.prijs !== 'number') {
      values.prijs = Number(values.prijs);
    }

    const entity = {
      ...genotenopleidingEntity,
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
          ...genotenopleidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.genotenopleiding.home.createOrEditLabel" data-cy="GenotenopleidingCreateUpdateHeading">
            Create or edit a Genotenopleiding
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
                <ValidatedField name="id" required readOnly id="genotenopleiding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="genotenopleiding-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="genotenopleiding-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Datumtoewijzing"
                id="genotenopleiding-datumtoewijzing"
                name="datumtoewijzing"
                data-cy="datumtoewijzing"
                type="date"
              />
              <ValidatedField label="Prijs" id="genotenopleiding-prijs" name="prijs" data-cy="prijs" type="text" />
              <ValidatedField label="Verrekenen" id="genotenopleiding-verrekenen" name="verrekenen" data-cy="verrekenen" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/genotenopleiding" replace color="info">
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

export default GenotenopleidingUpdate;
