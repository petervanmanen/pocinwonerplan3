import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Historischpersoon from './historischpersoon';
import HistorischpersoonDetail from './historischpersoon-detail';
import HistorischpersoonUpdate from './historischpersoon-update';
import HistorischpersoonDeleteDialog from './historischpersoon-delete-dialog';

const HistorischpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Historischpersoon />} />
    <Route path="new" element={<HistorischpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<HistorischpersoonDetail />} />
      <Route path="edit" element={<HistorischpersoonUpdate />} />
      <Route path="delete" element={<HistorischpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HistorischpersoonRoutes;
