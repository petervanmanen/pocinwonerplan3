import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKlimplant } from 'app/shared/model/klimplant.model';
import { getEntity, updateEntity, createEntity, reset } from './klimplant.reducer';

export const KlimplantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const klimplantEntity = useAppSelector(state => state.klimplant.entity);
  const loading = useAppSelector(state => state.klimplant.loading);
  const updating = useAppSelector(state => state.klimplant.updating);
  const updateSuccess = useAppSelector(state => state.klimplant.updateSuccess);

  const handleClose = () => {
    navigate('/klimplant');
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
      ...klimplantEntity,
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
          ...klimplantEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.klimplant.home.createOrEditLabel" data-cy="KlimplantCreateUpdateHeading">
            Create or edit a Klimplant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="klimplant-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Hoogte" id="klimplant-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Knipfrequentie"
                id="klimplant-knipfrequentie"
                name="knipfrequentie"
                data-cy="knipfrequentie"
                type="text"
              />
              <ValidatedField
                label="Knipoppervlakte"
                id="klimplant-knipoppervlakte"
                name="knipoppervlakte"
                data-cy="knipoppervlakte"
                type="text"
              />
              <ValidatedField
                label="Ondersteuningsvorm"
                id="klimplant-ondersteuningsvorm"
                name="ondersteuningsvorm"
                data-cy="ondersteuningsvorm"
                type="text"
              />
              <ValidatedField label="Type" id="klimplant-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/klimplant" replace color="info">
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

export default KlimplantUpdate;
