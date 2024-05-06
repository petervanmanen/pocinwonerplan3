import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMagazijnplaatsing } from 'app/shared/model/magazijnplaatsing.model';
import { getEntity, updateEntity, createEntity, reset } from './magazijnplaatsing.reducer';

export const MagazijnplaatsingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const magazijnplaatsingEntity = useAppSelector(state => state.magazijnplaatsing.entity);
  const loading = useAppSelector(state => state.magazijnplaatsing.loading);
  const updating = useAppSelector(state => state.magazijnplaatsing.updating);
  const updateSuccess = useAppSelector(state => state.magazijnplaatsing.updateSuccess);

  const handleClose = () => {
    navigate('/magazijnplaatsing');
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
      ...magazijnplaatsingEntity,
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
          ...magazijnplaatsingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.magazijnplaatsing.home.createOrEditLabel" data-cy="MagazijnplaatsingCreateUpdateHeading">
            Create or edit a Magazijnplaatsing
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
                <ValidatedField name="id" required readOnly id="magazijnplaatsing-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beschrijving"
                id="magazijnplaatsing-beschrijving"
                name="beschrijving"
                data-cy="beschrijving"
                type="text"
              />
              <ValidatedField
                label="Datumgeplaatst"
                id="magazijnplaatsing-datumgeplaatst"
                name="datumgeplaatst"
                data-cy="datumgeplaatst"
                type="date"
              />
              <ValidatedField label="Herkomst" id="magazijnplaatsing-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField label="Key" id="magazijnplaatsing-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keydoos" id="magazijnplaatsing-keydoos" name="keydoos" data-cy="keydoos" type="text" />
              <ValidatedField
                label="Keymagazijnlocatie"
                id="magazijnplaatsing-keymagazijnlocatie"
                name="keymagazijnlocatie"
                data-cy="keymagazijnlocatie"
                type="text"
              />
              <ValidatedField label="Projectcd" id="magazijnplaatsing-projectcd" name="projectcd" data-cy="projectcd" type="text" />
              <ValidatedField
                label="Uitgeleend"
                id="magazijnplaatsing-uitgeleend"
                name="uitgeleend"
                data-cy="uitgeleend"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/magazijnplaatsing" replace color="info">
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

export default MagazijnplaatsingUpdate;
