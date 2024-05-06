import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoelstelling } from 'app/shared/model/doelstelling.model';
import { getEntities as getDoelstellings } from 'app/entities/doelstelling/doelstelling.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';
import { getEntities as getHoofdstuks } from 'app/entities/hoofdstuk/hoofdstuk.reducer';
import { IBegroting } from 'app/shared/model/begroting.model';
import { getEntities as getBegrotings } from 'app/entities/begroting/begroting.reducer';
import { IBegrotingregel } from 'app/shared/model/begrotingregel.model';
import { getEntity, updateEntity, createEntity, reset } from './begrotingregel.reducer';

export const BegrotingregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doelstellings = useAppSelector(state => state.doelstelling.entities);
  const products = useAppSelector(state => state.product.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const hoofdstuks = useAppSelector(state => state.hoofdstuk.entities);
  const begrotings = useAppSelector(state => state.begroting.entities);
  const begrotingregelEntity = useAppSelector(state => state.begrotingregel.entity);
  const loading = useAppSelector(state => state.begrotingregel.loading);
  const updating = useAppSelector(state => state.begrotingregel.updating);
  const updateSuccess = useAppSelector(state => state.begrotingregel.updateSuccess);

  const handleClose = () => {
    navigate('/begrotingregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoelstellings({}));
    dispatch(getProducts({}));
    dispatch(getKostenplaats({}));
    dispatch(getHoofdrekenings({}));
    dispatch(getHoofdstuks({}));
    dispatch(getBegrotings({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...begrotingregelEntity,
      ...values,
      betreftDoelstelling: doelstellings.find(it => it.id.toString() === values.betreftDoelstelling?.toString()),
      betreftProduct: products.find(it => it.id.toString() === values.betreftProduct?.toString()),
      betreftKostenplaats: kostenplaats.find(it => it.id.toString() === values.betreftKostenplaats?.toString()),
      betreftHoofdrekening: hoofdrekenings.find(it => it.id.toString() === values.betreftHoofdrekening?.toString()),
      betreftHoofdstuk: hoofdstuks.find(it => it.id.toString() === values.betreftHoofdstuk?.toString()),
      heeftBegroting: begrotings.find(it => it.id.toString() === values.heeftBegroting?.toString()),
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
          ...begrotingregelEntity,
          betreftDoelstelling: begrotingregelEntity?.betreftDoelstelling?.id,
          betreftProduct: begrotingregelEntity?.betreftProduct?.id,
          betreftKostenplaats: begrotingregelEntity?.betreftKostenplaats?.id,
          betreftHoofdrekening: begrotingregelEntity?.betreftHoofdrekening?.id,
          betreftHoofdstuk: begrotingregelEntity?.betreftHoofdstuk?.id,
          heeftBegroting: begrotingregelEntity?.heeftBegroting?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.begrotingregel.home.createOrEditLabel" data-cy="BegrotingregelCreateUpdateHeading">
            Create or edit a Begrotingregel
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
                <ValidatedField name="id" required readOnly id="begrotingregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Batenlasten"
                id="begrotingregel-batenlasten"
                name="batenlasten"
                data-cy="batenlasten"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Bedrag" id="begrotingregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Soortregel" id="begrotingregel-soortregel" name="soortregel" data-cy="soortregel" type="text" />
              <ValidatedField
                id="begrotingregel-betreftDoelstelling"
                name="betreftDoelstelling"
                data-cy="betreftDoelstelling"
                label="Betreft Doelstelling"
                type="select"
              >
                <option value="" key="0" />
                {doelstellings
                  ? doelstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="begrotingregel-betreftProduct"
                name="betreftProduct"
                data-cy="betreftProduct"
                label="Betreft Product"
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="begrotingregel-betreftKostenplaats"
                name="betreftKostenplaats"
                data-cy="betreftKostenplaats"
                label="Betreft Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="begrotingregel-betreftHoofdrekening"
                name="betreftHoofdrekening"
                data-cy="betreftHoofdrekening"
                label="Betreft Hoofdrekening"
                type="select"
              >
                <option value="" key="0" />
                {hoofdrekenings
                  ? hoofdrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="begrotingregel-betreftHoofdstuk"
                name="betreftHoofdstuk"
                data-cy="betreftHoofdstuk"
                label="Betreft Hoofdstuk"
                type="select"
              >
                <option value="" key="0" />
                {hoofdstuks
                  ? hoofdstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="begrotingregel-heeftBegroting"
                name="heeftBegroting"
                data-cy="heeftBegroting"
                label="Heeft Begroting"
                type="select"
              >
                <option value="" key="0" />
                {begrotings
                  ? begrotings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/begrotingregel" replace color="info">
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

export default BegrotingregelUpdate;
