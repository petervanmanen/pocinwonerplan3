import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInburgeraar } from 'app/shared/model/inburgeraar.model';
import { getEntity, updateEntity, createEntity, reset } from './inburgeraar.reducer';

export const InburgeraarUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inburgeraarEntity = useAppSelector(state => state.inburgeraar.entity);
  const loading = useAppSelector(state => state.inburgeraar.loading);
  const updating = useAppSelector(state => state.inburgeraar.updating);
  const updateSuccess = useAppSelector(state => state.inburgeraar.updateSuccess);

  const handleClose = () => {
    navigate('/inburgeraar');
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
      ...inburgeraarEntity,
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
          ...inburgeraarEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inburgeraar.home.createOrEditLabel" data-cy="InburgeraarCreateUpdateHeading">
            Create or edit a Inburgeraar
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="inburgeraar-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Doelgroep" id="inburgeraar-doelgroep" name="doelgroep" data-cy="doelgroep" type="text" />
              <ValidatedField
                label="Gedetailleerdedoelgroep"
                id="inburgeraar-gedetailleerdedoelgroep"
                name="gedetailleerdedoelgroep"
                data-cy="gedetailleerdedoelgroep"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inburgeraar" replace color="info">
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

export default InburgeraarUpdate;
