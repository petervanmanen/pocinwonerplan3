import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';
import { getEntities as getNummeraanduidings } from 'app/entities/nummeraanduiding/nummeraanduiding.reducer';
import { IAdresaanduiding } from 'app/shared/model/adresaanduiding.model';
import { getEntity, updateEntity, createEntity, reset } from './adresaanduiding.reducer';

export const AdresaanduidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nummeraanduidings = useAppSelector(state => state.nummeraanduiding.entities);
  const adresaanduidingEntity = useAppSelector(state => state.adresaanduiding.entity);
  const loading = useAppSelector(state => state.adresaanduiding.loading);
  const updating = useAppSelector(state => state.adresaanduiding.updating);
  const updateSuccess = useAppSelector(state => state.adresaanduiding.updateSuccess);

  const handleClose = () => {
    navigate('/adresaanduiding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNummeraanduidings({}));
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
      ...adresaanduidingEntity,
      ...values,
      verwijstnaarNummeraanduiding: nummeraanduidings.find(it => it.id.toString() === values.verwijstnaarNummeraanduiding?.toString()),
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
          ...adresaanduidingEntity,
          verwijstnaarNummeraanduiding: adresaanduidingEntity?.verwijstnaarNummeraanduiding?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.adresaanduiding.home.createOrEditLabel" data-cy="AdresaanduidingCreateUpdateHeading">
            Create or edit a Adresaanduiding
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
                <ValidatedField name="id" required readOnly id="adresaanduiding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresaanduiding"
                id="adresaanduiding-adresaanduiding"
                name="adresaanduiding"
                data-cy="adresaanduiding"
                type="text"
              />
              <ValidatedField
                id="adresaanduiding-verwijstnaarNummeraanduiding"
                name="verwijstnaarNummeraanduiding"
                data-cy="verwijstnaarNummeraanduiding"
                label="Verwijstnaar Nummeraanduiding"
                type="select"
              >
                <option value="" key="0" />
                {nummeraanduidings
                  ? nummeraanduidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adresaanduiding" replace color="info">
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

export default AdresaanduidingUpdate;
