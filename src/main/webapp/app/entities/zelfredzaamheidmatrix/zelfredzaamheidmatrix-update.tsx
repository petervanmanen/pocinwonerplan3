import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZelfredzaamheidmatrix } from 'app/shared/model/zelfredzaamheidmatrix.model';
import { getEntity, updateEntity, createEntity, reset } from './zelfredzaamheidmatrix.reducer';

export const ZelfredzaamheidmatrixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zelfredzaamheidmatrixEntity = useAppSelector(state => state.zelfredzaamheidmatrix.entity);
  const loading = useAppSelector(state => state.zelfredzaamheidmatrix.loading);
  const updating = useAppSelector(state => state.zelfredzaamheidmatrix.updating);
  const updateSuccess = useAppSelector(state => state.zelfredzaamheidmatrix.updateSuccess);

  const handleClose = () => {
    navigate('/zelfredzaamheidmatrix');
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
      ...zelfredzaamheidmatrixEntity,
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
          ...zelfredzaamheidmatrixEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zelfredzaamheidmatrix.home.createOrEditLabel" data-cy="ZelfredzaamheidmatrixCreateUpdateHeading">
            Create or edit a Zelfredzaamheidmatrix
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
                <ValidatedField name="id" required readOnly id="zelfredzaamheidmatrix-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindegeldigheid"
                id="zelfredzaamheidmatrix-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="text"
              />
              <ValidatedField
                label="Datumstartgeldigheid"
                id="zelfredzaamheidmatrix-datumstartgeldigheid"
                name="datumstartgeldigheid"
                data-cy="datumstartgeldigheid"
                type="text"
              />
              <ValidatedField label="Naam" id="zelfredzaamheidmatrix-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="zelfredzaamheidmatrix-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zelfredzaamheidmatrix" replace color="info">
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

export default ZelfredzaamheidmatrixUpdate;
