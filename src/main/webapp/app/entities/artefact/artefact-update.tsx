import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoos } from 'app/shared/model/doos.model';
import { getEntities as getDoos } from 'app/entities/doos/doos.reducer';
import { IArtefactsoort } from 'app/shared/model/artefactsoort.model';
import { getEntities as getArtefactsoorts } from 'app/entities/artefactsoort/artefactsoort.reducer';
import { IVondst } from 'app/shared/model/vondst.model';
import { getEntities as getVondsts } from 'app/entities/vondst/vondst.reducer';
import { IArtefact } from 'app/shared/model/artefact.model';
import { getEntity, updateEntity, createEntity, reset } from './artefact.reducer';

export const ArtefactUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doos = useAppSelector(state => state.doos.entities);
  const artefactsoorts = useAppSelector(state => state.artefactsoort.entities);
  const vondsts = useAppSelector(state => state.vondst.entities);
  const artefactEntity = useAppSelector(state => state.artefact.entity);
  const loading = useAppSelector(state => state.artefact.loading);
  const updating = useAppSelector(state => state.artefact.updating);
  const updateSuccess = useAppSelector(state => state.artefact.updateSuccess);

  const handleClose = () => {
    navigate('/artefact');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoos({}));
    dispatch(getArtefactsoorts({}));
    dispatch(getVondsts({}));
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
      ...artefactEntity,
      ...values,
      zitinDoos: doos.find(it => it.id.toString() === values.zitinDoos?.toString()),
      isvansoortArtefactsoort: artefactsoorts.find(it => it.id.toString() === values.isvansoortArtefactsoort?.toString()),
      bevatVondst: vondsts.find(it => it.id.toString() === values.bevatVondst?.toString()),
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
          ...artefactEntity,
          zitinDoos: artefactEntity?.zitinDoos?.id,
          isvansoortArtefactsoort: artefactEntity?.isvansoortArtefactsoort?.id,
          bevatVondst: artefactEntity?.bevatVondst?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.artefact.home.createOrEditLabel" data-cy="ArtefactCreateUpdateHeading">
            Create or edit a Artefact
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="artefact-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Artefectnummer"
                id="artefact-artefectnummer"
                name="artefectnummer"
                data-cy="artefectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Beschrijving" id="artefact-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField
                label="Conserveren"
                id="artefact-conserveren"
                name="conserveren"
                data-cy="conserveren"
                check
                type="checkbox"
              />
              <ValidatedField label="Datering" id="artefact-datering" name="datering" data-cy="datering" type="text" />
              <ValidatedField
                label="Dateringcomplex"
                id="artefact-dateringcomplex"
                name="dateringcomplex"
                data-cy="dateringcomplex"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Determinatieniveau"
                id="artefact-determinatieniveau"
                name="determinatieniveau"
                data-cy="determinatieniveau"
                type="text"
              />
              <ValidatedField label="Dianummer" id="artefact-dianummer" name="dianummer" data-cy="dianummer" type="text" />
              <ValidatedField label="Doosnummer" id="artefact-doosnummer" name="doosnummer" data-cy="doosnummer" type="text" />
              <ValidatedField label="Exposabel" id="artefact-exposabel" name="exposabel" data-cy="exposabel" check type="checkbox" />
              <ValidatedField label="Fotonummer" id="artefact-fotonummer" name="fotonummer" data-cy="fotonummer" type="text" />
              <ValidatedField label="Functie" id="artefact-functie" name="functie" data-cy="functie" type="text" />
              <ValidatedField label="Herkomst" id="artefact-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField label="Key" id="artefact-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keydoos" id="artefact-keydoos" name="keydoos" data-cy="keydoos" type="text" />
              <ValidatedField
                label="Keymagazijnplaatsing"
                id="artefact-keymagazijnplaatsing"
                name="keymagazijnplaatsing"
                data-cy="keymagazijnplaatsing"
                type="text"
              />
              <ValidatedField label="Keyput" id="artefact-keyput" name="keyput" data-cy="keyput" type="text" />
              <ValidatedField label="Keyvondst" id="artefact-keyvondst" name="keyvondst" data-cy="keyvondst" type="text" />
              <ValidatedField label="Literatuur" id="artefact-literatuur" name="literatuur" data-cy="literatuur" type="text" />
              <ValidatedField label="Maten" id="artefact-maten" name="maten" data-cy="maten" type="text" />
              <ValidatedField label="Naam" id="artefact-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Opmerkingen" id="artefact-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField label="Origine" id="artefact-origine" name="origine" data-cy="origine" type="text" />
              <ValidatedField
                label="Projectcd"
                id="artefact-projectcd"
                name="projectcd"
                data-cy="projectcd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Putnummer"
                id="artefact-putnummer"
                name="putnummer"
                data-cy="putnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Restauratiewenselijk"
                id="artefact-restauratiewenselijk"
                name="restauratiewenselijk"
                data-cy="restauratiewenselijk"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Tekeningnummer"
                id="artefact-tekeningnummer"
                name="tekeningnummer"
                data-cy="tekeningnummer"
                type="text"
              />
              <ValidatedField label="Type" id="artefact-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Vondstnummer" id="artefact-vondstnummer" name="vondstnummer" data-cy="vondstnummer" type="text" />
              <ValidatedField id="artefact-zitinDoos" name="zitinDoos" data-cy="zitinDoos" label="Zitin Doos" type="select">
                <option value="" key="0" />
                {doos
                  ? doos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="artefact-isvansoortArtefactsoort"
                name="isvansoortArtefactsoort"
                data-cy="isvansoortArtefactsoort"
                label="Isvansoort Artefactsoort"
                type="select"
              >
                <option value="" key="0" />
                {artefactsoorts
                  ? artefactsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artefact-bevatVondst" name="bevatVondst" data-cy="bevatVondst" label="Bevat Vondst" type="select">
                <option value="" key="0" />
                {vondsts
                  ? vondsts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artefact" replace color="info">
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

export default ArtefactUpdate;
