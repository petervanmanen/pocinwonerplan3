import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBouwtype } from 'app/shared/model/bouwtype.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwtype.reducer';

export const BouwtypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bouwtypeEntity = useAppSelector(state => state.bouwtype.entity);
  const loading = useAppSelector(state => state.bouwtype.loading);
  const updating = useAppSelector(state => state.bouwtype.updating);
  const updateSuccess = useAppSelector(state => state.bouwtype.updateSuccess);

  const handleClose = () => {
    navigate('/bouwtype');
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
      ...bouwtypeEntity,
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
          ...bouwtypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwtype.home.createOrEditLabel" data-cy="BouwtypeCreateUpdateHeading">
            Create or edit a Bouwtype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bouwtype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Hoofdcategorie"
                id="bouwtype-hoofdcategorie"
                name="hoofdcategorie"
                data-cy="hoofdcategorie"
                type="text"
              />
              <ValidatedField label="Subcategorie" id="bouwtype-subcategorie" name="subcategorie" data-cy="subcategorie" type="text" />
              <ValidatedField label="Toelichting" id="bouwtype-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwtype" replace color="info">
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

export default BouwtypeUpdate;
