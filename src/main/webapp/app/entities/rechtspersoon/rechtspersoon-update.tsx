import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKadastralemutatie } from 'app/shared/model/kadastralemutatie.model';
import { getEntities as getKadastralemutaties } from 'app/entities/kadastralemutatie/kadastralemutatie.reducer';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './rechtspersoon.reducer';

export const RechtspersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kadastralemutaties = useAppSelector(state => state.kadastralemutatie.entities);
  const rechtspersoonEntity = useAppSelector(state => state.rechtspersoon.entity);
  const loading = useAppSelector(state => state.rechtspersoon.loading);
  const updating = useAppSelector(state => state.rechtspersoon.updating);
  const updateSuccess = useAppSelector(state => state.rechtspersoon.updateSuccess);

  const handleClose = () => {
    navigate('/rechtspersoon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKadastralemutaties({}));
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
      ...rechtspersoonEntity,
      ...values,
      betrokkenenKadastralemutaties: mapIdList(values.betrokkenenKadastralemutaties),
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
          ...rechtspersoonEntity,
          betrokkenenKadastralemutaties: rechtspersoonEntity?.betrokkenenKadastralemutaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rechtspersoon.home.createOrEditLabel" data-cy="RechtspersoonCreateUpdateHeading">
            Create or edit a Rechtspersoon
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
                <ValidatedField name="id" required readOnly id="rechtspersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresbinnenland"
                id="rechtspersoon-adresbinnenland"
                name="adresbinnenland"
                data-cy="adresbinnenland"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland"
                id="rechtspersoon-adresbuitenland"
                name="adresbuitenland"
                data-cy="adresbuitenland"
                type="text"
              />
              <ValidatedField
                label="Adrescorrespondentie"
                id="rechtspersoon-adrescorrespondentie"
                name="adrescorrespondentie"
                data-cy="adrescorrespondentie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Emailadres" id="rechtspersoon-emailadres" name="emailadres" data-cy="emailadres" type="text" />
              <ValidatedField
                label="Faxnummer"
                id="rechtspersoon-faxnummer"
                name="faxnummer"
                data-cy="faxnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Identificatie"
                id="rechtspersoon-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Kvknummer"
                id="rechtspersoon-kvknummer"
                name="kvknummer"
                data-cy="kvknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="rechtspersoon-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Rechtsvorm"
                id="rechtspersoon-rechtsvorm"
                name="rechtsvorm"
                data-cy="rechtsvorm"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Rekeningnummer"
                id="rechtspersoon-rekeningnummer"
                name="rekeningnummer"
                data-cy="rekeningnummer"
                type="text"
              />
              <ValidatedField
                label="Telefoonnummer"
                id="rechtspersoon-telefoonnummer"
                name="telefoonnummer"
                data-cy="telefoonnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Betrokkenen Kadastralemutatie"
                id="rechtspersoon-betrokkenenKadastralemutatie"
                data-cy="betrokkenenKadastralemutatie"
                type="select"
                multiple
                name="betrokkenenKadastralemutaties"
              >
                <option value="" key="0" />
                {kadastralemutaties
                  ? kadastralemutaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rechtspersoon" replace color="info">
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

export default RechtspersoonUpdate;
