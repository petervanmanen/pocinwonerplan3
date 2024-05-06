import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Appartementsrechtsplitsing from './appartementsrechtsplitsing';
import AppartementsrechtsplitsingDetail from './appartementsrechtsplitsing-detail';
import AppartementsrechtsplitsingUpdate from './appartementsrechtsplitsing-update';
import AppartementsrechtsplitsingDeleteDialog from './appartementsrechtsplitsing-delete-dialog';

const AppartementsrechtsplitsingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Appartementsrechtsplitsing />} />
    <Route path="new" element={<AppartementsrechtsplitsingUpdate />} />
    <Route path=":id">
      <Route index element={<AppartementsrechtsplitsingDetail />} />
      <Route path="edit" element={<AppartementsrechtsplitsingUpdate />} />
      <Route path="delete" element={<AppartementsrechtsplitsingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppartementsrechtsplitsingRoutes;
