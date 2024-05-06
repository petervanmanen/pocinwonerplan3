import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { ITenaamstelling } from 'app/shared/model/tenaamstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './tenaamstelling.reducer';

export const TenaamstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const tenaamstellingEntity = useAppSelector(state => state.tenaamstelling.entity);
  const loading = useAppSelector(state => state.tenaamstelling.loading);
  const updating = useAppSelector(state => state.tenaamstelling.updating);
  const updateSuccess = useAppSelector(state => state.tenaamstelling.updateSuccess);

  const handleClose = () => {
    navigate('/tenaamstelling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRechtspersoons({}));
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
      ...tenaamstellingEntity,
      ...values,
      heeftRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.heeftRechtspersoon?.toString()),
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
          ...tenaamstellingEntity,
          heeftRechtspersoon: tenaamstellingEntity?.heeftRechtspersoon?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tenaamstelling.home.createOrEditLabel" data-cy="TenaamstellingCreateUpdateHeading">
            Create or edit a Tenaamstelling
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
                <ValidatedField name="id" required readOnly id="tenaamstelling-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aandeelinrecht"
                id="tenaamstelling-aandeelinrecht"
                name="aandeelinrecht"
                data-cy="aandeelinrecht"
                type="text"
              />
              <ValidatedField
                label="Burgerlijkestaattentijdevanverkrijging"
                id="tenaamstelling-burgerlijkestaattentijdevanverkrijging"
                name="burgerlijkestaattentijdevanverkrijging"
                data-cy="burgerlijkestaattentijdevanverkrijging"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheid"
                id="tenaamstelling-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="tenaamstelling-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Exploitantcode"
                id="tenaamstelling-exploitantcode"
                name="exploitantcode"
                data-cy="exploitantcode"
                type="text"
              />
              <ValidatedField
                label="Identificatietenaamstelling"
                id="tenaamstelling-identificatietenaamstelling"
                name="identificatietenaamstelling"
                data-cy="identificatietenaamstelling"
                type="text"
              />
              <ValidatedField
                label="Verklaringinzakederdenbescherming"
                id="tenaamstelling-verklaringinzakederdenbescherming"
                name="verklaringinzakederdenbescherming"
                data-cy="verklaringinzakederdenbescherming"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Verkregennamenssamenwerkingsverband"
                id="tenaamstelling-verkregennamenssamenwerkingsverband"
                name="verkregennamenssamenwerkingsverband"
                data-cy="verkregennamenssamenwerkingsverband"
                type="text"
              />
              <ValidatedField
                id="tenaamstelling-heeftRechtspersoon"
                name="heeftRechtspersoon"
                data-cy="heeftRechtspersoon"
                label="Heeft Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tenaamstelling" replace color="info">
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

export default TenaamstellingUpdate;
